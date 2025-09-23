package ch.zhaw.ads;

public class BracketServer implements CommandExecutor {

    public boolean checkBrackets(String s) {
        Stack stack = new ListStack();

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if(c == '/' && s.charAt(i +1) == '*') {
                stack.push("/*");
                i++;
            } else if(c == '*' && s.charAt(i +1) == '/') {
                if(stack.isEmpty() || !stack.pop().equals("/*")) {
                    return false;
                }
                i++;

            } else if(c == '(' || c == '{' || c == '[' || c == '<') {
                stack.push(c);
            } else if(c == ')' || c == '}' || c == ']' || c == '>') {
                if(stack.isEmpty()) {
                    return false;
                }

                // if it starts with bracket type it needs to end with the same type
                char open = (char) stack.pop();
                if ((c == ')' && open != '(') ||
                        (c == '}' && open != '{') ||
                        (c == ']' && open != '[') ||
                        (c == '>' && open != '<')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    @Override
    public String execute(String command) throws Exception {
        return Boolean.toString(checkBrackets(command));
    }
}