/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumslidere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author martin
 */
public class SumSlider {
    private long count = 0;
    private int n = -1;
    private int begin = 0;
    private int end = 0;
    private boolean solved = false;

    SumSlider stringToN(String aNumber) {
        n = Integer.valueOf(aNumber);
        return this;
    }

    private int sumBeginToEnd() {
        //System.out.println(String.format("begin=%d, end=%d", begin, end));
        int sum = 0;
        for (int i = begin; i <= end; i++) {
            sum += i;
        }
        return sum;
    }

    SumSlider solve() {
        int sum = 0;
        begin = 1;
        end  = 2;
        while (sum != n && end - begin < n + 1 / 2 && end < n) {
            count++;
            sum = sumBeginToEnd();
            if (sum < n) {
                end++;
            }
            if (sum > n) {
                begin++;
            }
        }
        solved = sum == n;
        return this;
    }
    
    SumSlider solveReverse() {
        int sum = 0;
        end  = (n+1) / 2;
        begin = end - 1;
        while (sum != n && end - begin < (n + 1) / 2 && end < n) {
            count++;
            sum = sumBeginToEnd();
            if (sum < n) {
                begin--;
            }
            if (sum > n) {
                end--;
            }
        }
        solved = sum == n;
        return this;       
    }

    void println() {
        if (!solved || begin == 0) {
            System.out.println("There was no solution found or you havend looked of it yet!");
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = begin; i <= end; i++) {
                sb.append(i);
                sb.append("+");
            }
            sb.delete(sb.length() - 1, sb.length());
            System.out.println(sb.toString() + " = " + n + " (found in " + count + " tries)");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Enter number or q for to exit: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = reader.readLine();
            if ("q".equals(line.trim()) || "Q".equals(line.trim())) {
                break;
            }
            new SumSlider().stringToN(line).solve().println();
            new SumSlider().stringToN(line).solveReverse().println();
        }
    }
}
