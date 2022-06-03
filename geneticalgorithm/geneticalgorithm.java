package geneticalgorithm;

import java.util.Arrays;

public class geneticalgorithm {
    public double solve(int nCandidates, Problem p){
        int nGenenrations = 1000;
        double fx = 0;
        double x = 0;
        double[] candidates = new double[nCandidates];//후보자들
        double[] selected = new double[nCandidates]; // select 결과
        for(int i=0;i<candidates.length;i++){
            candidates[i] = (int)(Math.random()*31); //1,29,3,10
        }

        for(int i=0;i<nGenenrations;i++){
            System.out.println(i+"번째 트라이");
            selected = select(candidates,p);
            candidates = crossover(candidates,selected,p);
            candidates = mutate(candidates);
            System.out.println(Arrays.toString(candidates));

            for(int j=0;j<candidates.length;j++){
                if(fx < p.fit(candidates[j])){
                    fx = p.fit(candidates[j]);
                    x = candidates[j];
                }
            }
         }

        return x;
    }

    private double[] select(double[] candidates, Problem p) {
        double circle =0;
        double [] fitness = candidates.clone();
        double [] selectedCandidates = new double[candidates.length];

        for(int i = 0;i< fitness.length;i++){//이니셜라이징
            selectedCandidates[i] = -1;
        }

        for(int i = 0;i< fitness.length;i++){//적합도 f(x) 대입
            fitness[i] = p.fit(candidates[i]);//117,341,185,360
        }

        for(int i=0;i< fitness.length;i++){
            circle += fitness[i];// 총적합도 계산
        }

        for(int i=0;i< fitness.length;i++){
            fitness[i] = fitness[i] / circle * 100; //비율생성 12%,34%,18%,36%
        }

        for(int i=1;i< fitness.length;i++){
            fitness[i] += fitness[i-1]; // 1-12 // 13-46 // 47-64 // 65-100
        }

        //System.out.println(Arrays.toString(fitness)); // check System.out.print("fitness");
        for(int i=0;i<fitness.length;i++){
            double R = Math.random()*99+1; // 1~100
            //System.out.print(R+" ");
            for(int j=0;j< fitness.length;j++){//0 1 2 3
                if(j==0){
                    if(1<=R && R<=fitness[j]){// 1~12
                        selectedCandidates[j]++;
                    }
                }
                else if(fitness[j-1]+1<=R && R<=fitness[j]){ // 13~46 // 47~64 // 65~100
                    selectedCandidates[j]++;
                }
            }
        }
        //System.out.println();
       // System.out.println(Arrays.toString(candidates));
        //System.out.println(Arrays.toString(selectedCandidates));
        return selectedCandidates;
    }

    private double[] crossover(double[] candidates,double[] selected, Problem p) {
        double[] crossfield = new double[candidates.length];
        int k =0;
        for(int i=0;i< candidates.length;i++){//crossfield에 차례로 값들 대입

            if(selected[i] != -1){
                while(selected[i] != -1){
                    crossfield[k] = candidates[i];
                    selected[i]--;// 0-> -1
                    k++;//2.0 17.0
                }
            }
        }
       // System.out.println(Arrays.toString(crossfield));
        for(int i=1;i<= crossfield.length;i=i+2){//비트 교차
            String str1 = Integer.toBinaryString((int)crossfield[i-1]);//ex 1011,10111
            //System.out.println(str1+" "+i+"번째");
            String str2 = Integer.toBinaryString((int)crossfield[i]);
           // System.out.println(str2+" "+i+"번째");

            if(str1.length() > str2.length()){//str1이 더 길 경우
                while(str1.length() != str2.length()){
                    str2 = "0"+ str2;
                }
            }
            else if(str1.length() < str2.length()){//str2가 더 길 경우 ex 01011,10111
                while(str1.length() != str2.length()){
                    str1 = "0"+ str1;
                }
            }
            //System.out.println("str1 : "+str1+", str2 : "+str2);
            String str1_cut1 = str1.substring(0,str1.length()/2);//01
            String str1_cut2 = str1.substring(str1.length()/2,str1.length());//011

            String str2_cut1 = str2.substring(0,str2.length()/2);//10
            String str2_cut2 = str2.substring(str2.length()/2,str2.length());//111

            String s1 = str1_cut1 + str2_cut2;//01111
            //System.out.println(s1);
            String s2 = str2_cut1 + str1_cut2;//10011
            //System.out.println(s2);

            crossfield[i-1] = Integer.parseInt(s1,2);
            crossfield[i] = Integer.parseInt(s2,2);
        }
        //System.out.println(Arrays.toString(crossfield));
        return crossfield;
    }

    private double[] mutate(double[] candidates) {
        String s = Integer.toBinaryString((int) candidates[0]);
        //System.out.println("0번째 인덱스 값 : "+s);
        char[] charArr = s.toCharArray();
        if(charArr[1] == '1'){
            charArr[1] = '0';
        }
        else
            charArr[1] = '1';
        String ss = new String(charArr);
        candidates[0] = Integer.parseInt(ss,2);
        //System.out.println("바뀐 값 : " +candidates[0]);
        return candidates;
    }
}
