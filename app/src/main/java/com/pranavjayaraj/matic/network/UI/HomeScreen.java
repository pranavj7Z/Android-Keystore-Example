package com.pranavjayaraj.matic.network.UI;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.pranavjayaraj.matic.network.Adapter.TokenListAdapter;
import com.pranavjayaraj.matic.network.R;
import com.pranavjayaraj.matic.network.Util.AESCrypt;
import com.pranavjayaraj.matic.network.Dialogs.HashDialog;
import com.pranavjayaraj.matic.network.Dialogs.TokenDialog;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeScreen extends AppCompatActivity {
    private List<String> sources;
    final static String src[] = {
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/0xbtc.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/$pac.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/2give.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/aeon.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/ark.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/bco.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/bcn.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/ctxc.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/edo.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/enj.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/eth.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/etc.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/btc.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/dash.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/drgn.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/ins.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/omni.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/ae.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/bnb.png",
            "https://cdn.jsdelivr.net/gh/atomiclabs/cryptocurrency-icons@95af504a411054bbd7a2fb97a84a2c2831d334cc/128/icon/vib.png"
    };

    final static String token[] = {"0XBTC","$PAC","2GIVE","AEON","ARK","BCO","BCN","CTXC","EDO","ENJ","ETH","ETC","BTC","DASH","DRGN","INS","OMNI","AE","BNB","VIB"};
    final static String coin[] = {"0xBitcoin","PACcoin","2Give","Aeon","Ark","Bananacoin","Bytecoin","Cortex","Eidoo","Enjin Coin","Ethereum","Ethereum classic","Bitcoin","Dash","Dragoncoin","Insolar","Omni","Aeternity","Binance coin","Viberate"};
    final static String value[] = {"34","45","32","77","66","23","64","100","89","76","56","65","75","35","64","54","73","86","79","15"};

    ListView imageList;
    String USERNAME ,PASSWORD,AES_HASH;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    TokenDialog tokenDialog;
    HashDialog hashDialog;
    Button hash;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        hash = (Button) findViewById(R.id.hash);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        imageList = (ListView) findViewById(R.id.imagelist);
        final ArrayList<String> srcList = new ArrayList<String>(Arrays.asList(src));
        final ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList(token));
        final ArrayList<String> coinList = new ArrayList<String>(Arrays.asList(coin));
        final ArrayList<String> valueList = new ArrayList<String>(Arrays.asList(value));
        USERNAME = getIntent().getStringExtra("USERNAME");
        AES_HASH =getIntent().getStringExtra("AES_HASH"+USERNAME);
        PASSWORD = getIntent().getStringExtra("PASSWORD");
        TokenListAdapter tokenListAdapter = new TokenListAdapter(this,srcList,tokenList,coinList,valueList);
        imageList.setAdapter(tokenListAdapter);
        tokenDialog = new TokenDialog(this);
        hashDialog = new HashDialog(this);

        hash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String hashcode = AESCrypt.decrypt(USERNAME + PASSWORD,AES_HASH);
                    hashDialog.show();
                    hashDialog.setToken(hashcode);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
            }
        });

        imageList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               tokenDialog.show();
                tokenDialog.setImage(srcList.get(i),coinList.get(i));
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return false;
            }
        });

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (tokenDialog.isShowing()) {
                    tokenDialog.dismiss();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (tokenDialog.isShowing()) {
                    tokenDialog.dispatchTouchEvent(ev);
                }
                break;
        }
        if (tokenDialog.isShowing()) {
            imageList.clearFocus();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

}
