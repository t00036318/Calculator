package van.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Integer a = null;
    Integer b = null;
    String o;
    float res;
    TextView result, logs;

    private static final String URL_STRING = "http://162.243.64.94/dm.php";
    private static final String TAG = "Calculator";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        logs = (TextView) findViewById(R.id.logs);
    }


    public void numero(View v){
        Button bt = (Button) v;
        String buttonText = bt.getText().toString();
        CharSequence aux, aux2;
        if (!result.getText().equals("+") && !result.getText().equals("-") && !result.getText().equals("*") && !result.getText().equals("/") && !result.getText().equals("=")){
            if (!buttonText.equals("+") && !buttonText.equals("-") && !buttonText.equals("*") && !buttonText.equals("/") && !buttonText.equals("=")){
                result.setText(result.getText() + buttonText);
            }else{
                o = operacion(bt);
                aux = result.getText();
                logs.setText("Asignando variable a (primer número)");
                a = Integer.parseInt(aux.toString());
                result.setText(buttonText);
            }
        }else{
            if (b == null){
                result.setText(buttonText);
                aux2 = buttonText;
                logs.setText("Asignando variable b (segundo número)");
                b = Integer.parseInt(aux2.toString());
            }else{
                if (!buttonText.equals("+") && !buttonText.equals("-") && !buttonText.equals("*") && !buttonText.equals("/") && !buttonText.equals("=")) {
                    result.setText(result.getText() + buttonText);
                }else{
                aux2 = result.getText();
                b = Integer.parseInt(aux2.toString());
                result.setText(buttonText);
            }
            }
        }
        if(buttonText.equals("=")){
            ejecutar(o, a, b);
            try {
                logs.setText("Haciendo POST");
                postData();
            }catch(IOException ex) {
            }
        }

    }


    public String operacion(View v) {
        Button bt = (Button) v;
        String buttonText = bt.getText().toString();
        result.setText(buttonText);
        logs.setText("Asignando variable o (operación)");
        if (buttonText.equals("+")) {
            o = "sum";
        }
        if (buttonText.equals("-")) {
            o = "res";
        }
        if (buttonText.equals("*")) {
            o = "mul";
        }
        if (buttonText.equals("/")) {
            o = "div";
        }
        result.setText(buttonText);
        return o;
    }

    public void ejecutar(String o, int a, int b){
        if (o.equals("sum")) {
            res = a + b;
        }
        if (o.equals("res")) {
            res = a - b;
        }
        if (o.equals("mul")) {
            res = a * b;
        }
        if (o.equals("div") && b != 0) {
            res = a / b;
        } else {
            result.setText("Indefinido");
        }

        result.setText(String.valueOf(res));
    }


    public void postData() throws IOException, ClientProtocolException {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("o", o));
        nameValuePairs.add(new BasicNameValuePair("a", a.toString()));
        nameValuePairs.add(new BasicNameValuePair("b", b.toString()));

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL_STRING);
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = httpclient.execute(httppost);
        result.setText(response.toString());
    }
}
