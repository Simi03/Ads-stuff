package ch.zhaw.ads;

public class WellformedXmlServer implements CommandExecutor {

    public boolean checkWellformed(String xml) {
        Stack stack = new ListStack();

        int i = 0;
        while (i < xml.length()) {
            if (xml.charAt(i) != '<') return false;

            int j = xml.indexOf('>', i);
            if (j == -1) return false; // no closing bracket

            String tag = xml.substring(i + 1, j).trim();

            if (tag.endsWith("/")) {
                // self-closing tag like <a/>
            } else if (tag.startsWith("/")) {
                // closing tag like </a>
                String name = tag.substring(1).trim();
                if (stack.isEmpty() || !stack.pop().equals(name)) return false;
            } else {
                String name = tag.split("\\s+", 2)[0];
                stack.push(name);
            }

            i = j + 1;
        }

        return stack.isEmpty();
    }





    @Override
    public String execute(String command) throws Exception {
        return Boolean.toString(checkWellformed(command));
    }
}
