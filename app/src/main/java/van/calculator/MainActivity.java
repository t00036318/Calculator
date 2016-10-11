package van.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    String a = "";
    String b = "default";
    String o = "default";
    TextView result, logs;

    private static final String URL_STRING = "http://162.243.64.94/dm.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        logs = (TextView) findViewById(R.id.logs);
    }

    public void borrar(View v){
        a = "";
        b = "default";
        o = "default";
        logs.setText("");
        result.setText("");
    }

    public void numero(View v){
        Button bt = (Button) v;
        String buttonText = bt.getText().toString();
        if(a.equals("default")){
            logs.setText("");
            result.setText("");
        }
        if (o.equals("default")){
            result.setText(result.getText() + buttonText);
            a = result.getText().toString();
            } else
                if(!o.equals("default")){
                    if (b.equals("default")){
                        result.setText("");
                        result.setText(buttonText);
                    }
                    else {
                        result.setText(result.getText() + buttonText);
                    }
                    b = result.getText().toString();
                }
    }


    public void operacion(View v) {

        logs.setText(logs.getText() + "Variable a (primer número) asignada");

        Button bt = (Button) v;
        String buttonText = bt.getText().toString();
        result.setText(buttonText);

        logs.setText(logs.getText() + "\nAsignando variable o (operación)");


        if (buttonText.equals("+")) {
            o = "sum";
        } else if (buttonText.equals("-")) {
                o = "res";
            } else if (buttonText.equals("*")) {
                o = "mul";
            } else if (buttonText.equals("/")) {
                    o = "div";
        }
    }

    public void ejecutar (View v){
        logs.setText(logs.getText() + "\nVariable b (segundo número) asignada");

        logs.setText(logs.getText() + "\nHaciendo petición al servidor...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        logs.setText(logs.getText() + "\nRespuesta del servidor en pantalla");
                        result.setText(response);
                        a = "default";
                        b = "default";
                        o = "default";
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        logs.setText(logs.getText() + "\nError: conexión, división entre 0 o parámetros faltantes");
                        a = "default";
                        b = "default";
                        o = "default";
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("o", o);
                params.put("a", a);
                params.put("b", b);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

}
