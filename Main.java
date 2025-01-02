import org.json.JSONObject;
import java.math.BigInteger;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try {
            // Simulated JSON Input
            String jsonString = "{ \"keys\": { \"n\": 4, \"k\": 3 }, \"1\": { \"base\": \"10\", \"value\": \"4\" }, \"2\": { \"base\": \"2\", \"value\": \"111\" }, \"3\": { \"base\": \"10\", \"value\": \"12\" }, \"6\": { \"base\": \"4\", \"value\": \"213\" } }";

            JSONObject jsonInput = new JSONObject(jsonString);
            JSONObject keys = jsonInput.getJSONObject("keys");
            int n = keys.getInt("n");
            int k = keys.getInt("k");
            int m = k - 1;

            Map<Integer, BigInteger> xValues = new HashMap<>();
            Map<Integer, BigInteger> yValues = new HashMap<>();

            // Extract and Decode Roots
            for (String key : jsonInput.keySet()) {
                if (!key.equals("keys")) {
                    JSONObject root = jsonInput.getJSONObject(key);
                    int x = Integer.parseInt(key);
                    int base = root.getInt("base");
                    String value = root.getString("value");
                    BigInteger y = new BigInteger(value, base);

                    xValues.put(x, BigInteger.valueOf(x));
                    yValues.put(x, y);
                }
            }

            // Compute Constant Term 'c'
            BigInteger c = lagrangeInterpolation(xValues, yValues, m);

            // Output the result
            System.out.println("The constant term 'c' is: " + c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lagrange Interpolation to find constant term 'c'
    private static BigInteger lagrangeInterpolation(Map<Integer, BigInteger> xValues, Map<Integer, BigInteger> yValues, int degree) {
        BigInteger result = BigInteger.ZERO;

        for (int i : xValues.keySet()) {
            BigInteger xi = xValues.get(i);
            BigInteger yi = yValues.get(i);

            BigInteger term = yi;
            for (int j : xValues.keySet()) {
                if (i != j) {
                    BigInteger xj = xValues.get(j);
                    term = term.multiply(xj).divide(xj.subtract(xi));
                }
            }
            result = result.add(term);
        }
        return result;
    }
}
