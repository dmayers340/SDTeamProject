
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunnableThing implements Runnable {
	
	public RunnableThing() {
		
	}

	public void run() {
		
        BufferedReader br = null;

        try {

            br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                String input = br.readLine();

                if ("q".equals(input)) {
                    System.exit(0);
                }

                System.out.println("input : " + input);
                System.out.println("-----------\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
