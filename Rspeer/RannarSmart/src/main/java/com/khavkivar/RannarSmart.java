package com.khavkivar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.khavkivar.impl.Buying;
import com.khavkivar.impl.MakePotion;
import com.khavkivar.model.ItemGe;
import com.khavkivar.model.ItemOffer;
import org.json.JSONObject;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


@ScriptMeta(developer = "KhavKivar",
        name ="RannarSmart",
        desc = "Make rannar unf potion on gran exchange",
        category = ScriptCategory.MONEY_MAKING,
        version = 0.01)
public class RannarSmart extends TaskScript {
    private static final Task[] TASKS = {
            new Buying()
    };
    // GE Stack
    public static Stack<ItemOffer> stack_items = new Stack<ItemOffer>();


    public static final int VIAL_ID = 227;
    public static final int RANARR_ID = 257;
    public static final int RANARR_UNF_ID = 99;
    public static final int RANARR_UNF_NOTED_ID = 100;
    public static final int MONEY_ID = 995;
    public static  Map<String,ItemGe> ITEM_PRICES_ID = new HashMap<String, ItemGe>();


    @Override
    public void onStart() {
        Log.fine("Starting");
        URL url = null;
        try {
            url = new URL("https://rsbuddy.com/exchange/summary.json");
            InputStream inputStream = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json =new JSONObject(jsonText);

            ITEM_PRICES_ID = new Gson().fromJson(
                    json.toString(), new TypeToken<HashMap<String, ItemGe>>() {}.getType()
            );


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    submit(TASKS);

    }

    public  static boolean isStackEmpty(){
        return stack_items.size() == 0 ? true :false;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


}
