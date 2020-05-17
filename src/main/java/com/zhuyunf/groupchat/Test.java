package com.zhuyunf.groupchat;

public class Test {
    public static void main(String[] args) {
        char c = chang('B');
        System.out.println(c);
        char a = 'A';
        System.out.println((int)a);
    }

    public static char chang(char c) {
        if (c >= 'A' && c <= 'Z') {
            c = (char) (c + 32);
        }
        if (c >= 'a' && c <= 'z') {
            c = (char) (c - 32);
        }
            return   c;

    }
}
