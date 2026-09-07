package stacksqueues;

import interfaces.Stack;

class BracketChecker {
    private String input;

    public BracketChecker(String in) {
        input = in;
    }

    public void check() {
        Stack<Character> stack = new ArrayStack<>();

        for (char ch : input.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    System.out.println("Not correct; extra closing bracket: " + ch);
                    return;
                }
                char last = stack.pop();
                if ((last == '(' && ch != ')') ||
                        (last == '{' && ch != '}') ||
                        (last == '[' && ch != ']')) {
                    System.out.println("Not correct;: " + last + " doesnt match " + ch);
                    return;
                }
            }
        }

        if (!stack.isEmpty()) {
            System.out.println("Not correct; Nothing matches opening");
        } else {
            System.out.println("Correct");
        }
    }

    public static void main(String[] args) {
        String [] inputs = {
                "[]]()()", // not correct
                "c[d]", // correct\n" +
                "a{b[c]d}e", // correct\n" +
                "a{b(c]d}e", // not correct; ] doesn't match (\n" +
                "a[b{c}d]e}", // not correct; nothing matches final }\n" +
                "a{b(c) ", // // not correct; Nothing matches opening {
        };

        for(String input : inputs) {
            BracketChecker checker = new BracketChecker(input);
            System.out.println("checking: " + input);
            checker.check();
        }
    }
}