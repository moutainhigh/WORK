package com.xlkfinance.bms.server.workflow;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017年11月30日下午5:53:18 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class WorkFlowTest {
   static int i = 1;

   public static void main(String[] args) {
      Runnable target = new Runnable() {

         @Override
         public void run() {
            int monry = i++;
            new A().add(monry);
         }
      };
      new Thread(target).start();
      new Thread(target).start();
   }

}

class A {
   static A a=new A();
   static int money;

   public void add(int moneyTemp) {
      synchronized (a) {
         System.out.println(">>>线程：" + moneyTemp);
         int money = getMoney();
         if (moneyTemp == 1) {
            try {
               System.out.println(">>>线程sleep：moneyTemp：" + moneyTemp + ",money:" + money);
               Thread.sleep(3000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
         // 检查是否已经添加过
         if (money == 0) {
            A.money = moneyTemp;
            System.out.println("增加金额" + A.money);
         }
      }

   }

   public int getMoney() {
      return money;
   }
}