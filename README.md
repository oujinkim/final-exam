# **최적화 알고리즘을 이용한 회귀식 추정**
## **회귀식 추정**
### **회귀란?**
우리는 수많은 데이터들을 마주하며 살고 있다. 그 수많은 과거의 데이터를 활용해 수학적 모델을 만들고, 그 수학적 모델로 새로운 미래의 데이터를 예측할 수도 있다. 이를 통계학에서는 회귀라고 하고, 수학적 모델을 회귀식이라고 한다.  

### **데이터**
수많은 데이터를 회귀할때는 하나의 인과관계로 데이터들을 수집해야한다. 예를 들어 온도에 따른 아이스크림 판매량이 주제라고 한다면, 온도라는 원인의 영향과 이 원인의 변화로 생긴 아이스크림 판매량이라는 결과의 데이터를 수집해야한다. 이때 원인의 변수는 독립변수(Xi), 이 독립변수에 따라 변화가 생긴 결과는 종속변수(Yi)라고 한다.

### **손실함수**
수집한 독립변수와 종속변수의 데이터집합과 이를 가장 잘 설명할 수 있는 회귀식을 y=ax+b로 가정했을때, 회귀식의 값 yi와 실제 데이터값 ŷi의 오차가 발생한다. 회귀식을 설정할때, 이 오차가 적으면 적을수록, 데이터를 더 잘 설명할 수 있는 회귀식이 된다. 

이 오차를 적게 하기 위해, 손실함수를 정의한다. 손실함수란 예측한 값과 실제 값 사이의 평균 제곱 오차를 정의한다. N은 데이터의 개수를 의미한다. 필자는 평균을 나누는 것을 생략했다. 손실함수의 값이 작으면 작을수록 오차가 적음을 의미한다.

![images](https://velog.velcdn.com/images%2Frcchun%2Fpost%2Fac220735-2d93-46e0-8812-d9772b191c85%2Fimage.png)

### **유전 알고리즘**
유전 알고리즘은 다윈의 진화론으로부터 창안된 해 탐색 알고리즘으로, 적자생존의 개념으로 최적화 문제를 해결하는 알고리즘이다. 여러 연산들을 반복적으로 수행해 최적해 또는 최적해에 근접한 해가 될 수 있는 후보해를 찾아내는 것이 이 알고리즘의 목적이다. 필자는 이 유전 알고리즘을 통해서 구간내에서 손실함수의 가장 작은 값을 구하려고 한다. 

## **실제 예시**
다음은 필자가 선택한 데이터 집합, 야구팀 두산베어스의 팀승리기여도에 따른 승률이다.

![](https://user-images.githubusercontent.com/101345032/174300826-894ff7b1-d49f-4c29-baaa-d7ef49e8c1af.PNG)

독립변수는 기여도, 종속변수는 승률이다. 이에 따른 데이터집합의 그래프와 회귀식의 그래프는 다음과 같다. 회귀식 추정은 엑셀을 활용했다.

![](https://user-images.githubusercontent.com/101345032/174301226-40c8241b-1869-49ec-bb3e-2f329e11d931.PNG)

회귀식은 1차 회귀식 y= ax+b의 형태인 y=0.6385x + 36.903으로 추정했다. 이에 해당하는 모수 a값의 변화와 그에 따른 mse는 다음과 같다.
![](https://user-images.githubusercontent.com/101345032/174301934-aa03089e-a16b-4112-b253-5f8db50b0925.PNG)
손실함수는 y = 17524x^2 - 22379x + 7520.2가 나왔다.  

마찬가지로 모수 b값의 변화와 그에 따른 mse는 다음과 같다.
![](https://user-images.githubusercontent.com/101345032/174304932-7e737c5a-bd2b-4923-983d-160140b23eec.PNG)
손실함수는 y = 22x^2 - 1623.8x + 30337가 나왔다.

각각의 손실함수의 최소값을 구하기위해 유전 알고리즘을 활용했다. 유전 알고리즘의 코드는 아래와 같다.
``` java
public class geneticalgorithm {
    public double solve(int nCandidates, Problem p){
        int nGenenrations = 1000;
        double fx = Integer.MAX_VALUE;
        double x = Integer.MAX_VALUE;
        double[] candidates = new double[nCandidates];//후보자들
        double[] selected = new double[candidates.length]; // select 결과
```

nCandidates는 후보자들의 개수로, 후보자를 몇개 설정할지의 매개변수, p는 Problem이라는 인터페이스를 정의해 손실함수 y에 후보자 x를 넣을수 있게 구현한 매개변수이다. nGenenrations는 후보해의 세대로, 총 반복할 수를 의미한다. fx는 손실함수 y의 값을 저장할 변수이고, x는 최적해에 근접한 후보해의 값을 저장할 변수이다. 배열 candidates는 후보자들을 저장할 배열, selected는 선택연산의 결과값을 저장할 배열이다.

```java
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
```
총 2개의 케이스로 나누었으며, 후보자 배열에는 지정한 구간내의 랜덤값을 소수점 넷째자리까지 대입했다. 반복문을 실행해 손실함수의 작은 값이 나올때마다 fx에 저장하고 해당 후보자를 x에 저장했다. 또한 유전자 알고리즘의 대표적 3개의 연산인 선택연산의 select함수, 교차연산의 crossover함수, 돌연변이 연산의 mutate함수를 직접구현하여 실행시켰다.

```java
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
            fitness[i] = fitness[i] / circle * 100; //ex) 비율생성 12%,34%,18%,36%
        }

        for(int i=1;i< fitness.length;i++){
            fitness[i] += fitness[i-1]; // ex) 1-12 // 13-46 // 47-64 // 65-100
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
```
선택연산은 현재 세대의 후보해중 우수한 후보해를 선택하는 연산으로, 선택과정은 룰렛휠(후보해의 적합도에 비례하는 원반의 면적을 할당하고, 원반을 회전시켜 회전이 멈췄을때, 핀이 가리키는 후보해를 선택)방법을 활용했다. 룰렛휠 방법을 코드상으로는 직선을 그리고 적합도의 맞는 비율을 구간마다 설정해, 랜덤값이 그 비율에 들어가면 배열 selectedCandidates의 해당 원소값을 +1하여 선택되었음을 표시했다. circle은 원반의 변수, fitness는 적합도를 저장할 변수, selectedCandidates는 각각의 후보해가 선택된 횟수의 배열이다.

```java
private double[] crossover(double[] candidates,double[] selected, Problem p) {
        double[] crossfield = new double[candidates.length];
        int k =0;
        for(int i=0;i< candidates.length;i++){//crossfield에 차례로 값들 대입
            while(selected[i] != -1){
                crossfield[k] = candidates[i];
                selected[i]--;// 0-> -1
                k++;
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
```
교차연산은 염색체가 교차하는것을 모방한 연산으로, 2개의 후보해를 2진수로 표현하고 교차점 이후의 부분을 서로 교환하여 새로운 후보해를 만드는 방식을 활용했다. 교차점을 정하는 교차율은 0.5로 지정했다. 추가로 후보자가 실수형이기 때문에 실수형 이진수문자열함수는 없으므로 Integer.toBinaryString()함수의 사용을 위해 후보해에 10000을 곱해주었다. 또한 2개의 후보해의 길이가 다를수 있으므로 길이가 같아질때까지 앞에 0을 붙여주었다. 마지막으로 이진수문자열을 다시 정수로 바꿔주었다.

```java
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
```
돌연변이 연산은 후보해의 일부분을 임의로 변형시키는 것으로, 돌연변이가 일어날 확률인 돌연변이율은 0.333..으로 정했다. 돌연변이 연산을 위해 다시 후보해를 이진수문자열화 하였고, 돌연변이 방식은 2번째 인덱스의 원소값이 1이라면 0으로 바꾸어 주는 방식이다.

다음은 모수 a값의 유전 알고리즘 결과와 엑셀의 a와 손실함수의 그래프이다.
![](https://user-images.githubusercontent.com/101345032/174309318-77da78c4-4b19-47bc-b667-a6c55a9fed60.PNG)
![](https://user-images.githubusercontent.com/101345032/174309322-195b504d-791c-4087-a17c-5b908e4f1899.PNG)
![](https://user-images.githubusercontent.com/101345032/174302160-442056b4-ab61-448b-bceb-6adfe4ebdf62.PNG)

다음은 모수 b값의 유전 알고리즘 결과와 엑셀의 b와 손실함수의 그래프이다.
![](https://user-images.githubusercontent.com/101345032/174310003-5099cbcd-226f-4c28-94fe-29d65abd6b3f.PNG)
![](https://user-images.githubusercontent.com/101345032/174310011-baa76284-0c2e-4fde-b01d-9c5b78493d48.PNG)
![](https://user-images.githubusercontent.com/101345032/174310020-ecc69936-2086-45e2-9170-e5e33cdc473f.PNG)

**엑셀의 손실함수 그래프의 최소값과 유전알고리즘의 결과값이 유사함을 알수 있다.**

모수 a와 b와 손실함수의 관계의 그래프는 다음과 같다.
![](https://user-images.githubusercontent.com/101345032/174310496-94acbc4b-3a4a-4280-8d0f-5442e1ad6cb9.PNG)

### **장점과 한계**
유전 알고리즘을 진행하며 느낀점은 값이 유사하게 나올때와 값이 좀더 멀게 나올때 모두 랜덤이란 것이다. 좋은 값이 나오는것, 안좋은 값이 나오는것 모두 무작위성에 달려있고, 이를 해결하는 방법은 돌연변이율에 변화를 주거나, 교차율에 변화를 주는 방법, 또 다른 연산을 활용하는 것,등...이다.
