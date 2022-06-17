package geneticalgorithm;

import java.util.Arrays;

public class geneticalgorithm {
    public double solve(int nCandidates, Problem p){
        int nGenenrations = 1000;
        double fx = Integer.MAX_VALUE;
        double x = Integer.MAX_VALUE;
        double[] candidates = new double[nCandidates];//후보자들
        double[] selected = new double[candidates.length]; // select 결과
        for(int i=0;i<candidates.length;i++){
            candidates[i] = Math.random()*2; //0~2.0 까지의 x의 범위 // y= ax+b의 모수 a의 변화
            candidates[i] = Math.round(candidates[i]*10000)/10000.00;//0.1234 0.5678 0.6385 0.6666
//            candidates[i] = Math.random()*6+35; //35~41 // y= ax+b의 모수 b의 변화
//            candidates[i] = Math.round(candidates[i]*10000)/10000.00;
        }
        for(int i=0;i<=nGenenrations;i++){//1000
            for(int j=0;j<candidates.length;j++){
                if(fx > p.fit(candidates[j])){//최소값
                    fx = p.fit(candidates[j]);
                    x = candidates[j];
                }
            }
            System.out.println(i+"번째 트라이");
            selected = select(candidates,p);//선택연산후 각 후보가 선택된 횟수의 집합(1:2번,2:4번,...)
            //System.out.println("selected 성공");
            candidates = crossover(candidates,selected,p);
            //System.out.println("crossover 성공");
            candidates = mutate(candidates);
            //System.out.println("mutate 성공");
            System.out.println(Arrays.toString(candidates));

         }
        //System.out.println(fx);
        return x;
    }

    private double[] select(double[] candidates, Problem p) {
        double circle =0;
        double [] fitness = candidates.clone();
        double [] selectedCandidates = new double[candidates.length];//각 선택된 후보자의 선택된 횟수 배열

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

        for(int i=0;i<fitness.length;i++){
            double R = (int)(Math.random()*99+1); // 1~100
            for(int j=0;j< fitness.length;j++){//0 1 2 3
                if(j==0){
                    if(1<=R && R<=fitness[j]){// 1~12
                        selectedCandidates[j]++;
                    }
                }
                else if(fitness[j-1]+0.000001<=R && R<=fitness[j]){ // 13~46 // 47~64 // 65~100
                    selectedCandidates[j]++;
                }
            }
        }
        return selectedCandidates;
    }

    private double[] crossover(double[] candidates,double[] selected, Problem p) {
        double[] crossfield = new double[candidates.length];
        int k =0;
        for(int i=0;i< candidates.length;i++){//crossfield에 차례로 값들 대입
            while(selected[i] != -1){
                crossfield[k] = candidates[i];
                selected[i]--;// 0-> -1
                k++;//2.0 17.0
                }
            }
        for(int i =0;i< crossfield.length;i++){
            crossfield[i] *= 10000;//0.1234 -> 1234
        }
        for(int i=1;i<= crossfield.length;i=i+2){//비트 교차
            String str1 = Integer.toBinaryString((int)crossfield[i-1]);
            String str2 = Integer.toBinaryString((int)crossfield[i]);


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

            String str1_cut1 = str1.substring(0,str1.length()/2);//01
            String str1_cut2 = str1.substring(str1.length()/2,str1.length());//011

            String str2_cut1 = str2.substring(0,str2.length()/2);//10
            String str2_cut2 = str2.substring(str2.length()/2,str2.length());//111

            String s1 = str1_cut1 + str2_cut2;//01111

            String s2 = str2_cut1 + str1_cut2;//10011


            crossfield[i-1] = Integer.parseInt(s1,2);
            crossfield[i] = Integer.parseInt(s2,2);
        }

        return crossfield;
    }

    private double[] mutate(double[] candidates) {
        String s = Integer.toBinaryString((int) candidates[0]);

        char[] charArr = s.toCharArray();
        int R = (int)(Math.random()*100+1); //1~10
        if(charArr.length>1){//배열길이가 2 이상이면
            if(1<=R&&R<=33){// R : 1~50이면 (1/2확률), 1~33이면 (1/3확률)
                if(charArr[1] == '1'){//2번째 인덱스 돌연변이 1->0
                    charArr[1] = '0';
                }
            }
        }
        String ss = new String(charArr);
        candidates[0] = Integer.parseInt(ss,2);
        for(int i=0;i< candidates.length;i++){
            candidates[i] /= 10000.0;
        }
        return candidates;
    }
}
