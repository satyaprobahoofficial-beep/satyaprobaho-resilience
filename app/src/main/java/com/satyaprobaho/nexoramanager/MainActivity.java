package com.satyaprobaho.nexoramanager;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Typeface;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.text.InputType;

public class MainActivity extends Activity {
    private LinearLayout root;
    private WebView web;
    private SharedPreferences prefs;
    private final String satyaUrl = "https://satyaprobaho.satyaprobaho.workers.dev/admin.html";
    private final String nexoraDefault = "https://nexora-international-technology.vercel.app/";
    private final String satyaEmail = "satyaprobaho.official@gmail.com";
    private final String nexoraEmail = "computer26training@gmail.com";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        prefs = getSharedPreferences("manager_settings", MODE_PRIVATE);
        showHome();
    }

    private int dp(float v) { return (int)(v * getResources().getDisplayMetrics().density); }
    private TextView text(String value, int size, boolean bold) {
        TextView t = new TextView(this); t.setText(value); t.setTextSize(size);
        t.setTextColor(Color.rgb(28, 35, 48)); t.setPadding(dp(4), dp(8), dp(4), dp(8));
        if (bold) t.setTypeface(null, Typeface.BOLD); return t;
    }
    private Button button(String value, Runnable action) {
        Button b = new Button(this); b.setText(value);
        b.setOnClickListener(v -> action.run()); return b;
    }
    private LinearLayout column() {
        LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(18), dp(18), dp(18), dp(18)); return l;
    }
    private void base(View v) { setContentView(v); }
    private void showHome() {
        LinearLayout page = column();
        page.addView(text("Satyaprobaho & Nexora Manager", 23, true));
        page.addView(text("এক অ্যাপে দুই প্রতিষ্ঠানের আলাদা ব্যবস্থাপনা", 15, false));
        page.addView(text("প্রতিষ্ঠান নির্বাচন করুন", 18, true));
        page.addView(button("সত্যপ্রবাহ — Admin Login", () -> showCompany(true)));
        page.addView(text("লগইন ইমেইল: " + satyaEmail, 13, false));
        page.addView(button("Nexora International Technology — Login", () -> showCompany(false)));
        page.addView(text("লগইন ইমেইল: " + nexoraEmail, 13, false));
        page.addView(text("নিরাপত্তা: পাসওয়ার্ড বা API secret অ্যাপে সংরক্ষণ করা হয় না। ওয়েবসাইটের নিজস্ব লগইন ব্যবস্থায় সাইন ইন করুন।", 13, false));
        page.addView(text("সার্ভিস ব্যবহার/কোটা: যেসব provider API সংযুক্ত নয়, সেগুলোর হিসাব এখানে এখনো স্বয়ংক্রিয়ভাবে পাওয়া যাবে না।", 13, false));
        base(page);
    }
    private void showCompany(boolean satya) {
        LinearLayout page = column();
        page.addView(button("← প্রতিষ্ঠান নির্বাচন", this::showHome));
        page.addView(text(satya ? "সত্যপ্রবাহ ড্যাশবোর্ড" : "Nexora ড্যাশবোর্ড", 22, true));
        page.addView(text("অ্যাডমিন ইমেইল: " + (satya ? satyaEmail : nexoraEmail), 14, false));
        if (satya) {
            page.addView(button("সত্যপ্রবাহ Admin Panel খুলুন", () -> openWeb(satyaUrl)));
            page.addView(button("সত্যপ্রবাহ মূল ওয়েবসাইট", () -> openWeb("https://satyaprobaho.satyaprobaho.workers.dev/")));
        } else {
            EditText url = new EditText(this);
            url.setSingleLine(true); url.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
            url.setHint("আপনার Nexora Admin URL লিখুন");
            url.setText(prefs.getString("nexora_admin_url", nexoraDefault));
            page.addView(text("Nexora অ্যাডমিন URL", 15, true));
            page.addView(url);
            page.addView(button("URL সংরক্ষণ করুন", () -> {
                String value = url.getText().toString().trim();
                if (!value.startsWith("https://")) { Toast.makeText(this, "নিরাপত্তার জন্য https:// URL দিন", Toast.LENGTH_LONG).show(); return; }
                prefs.edit().putString("nexora_admin_url", value).apply();
                Toast.makeText(this, "URL সংরক্ষিত হয়েছে", Toast.LENGTH_SHORT).show();
            }));
            page.addView(button("Nexora Admin / Website খুলুন", () -> {
                String value = url.getText().toString().trim();
                if (!value.startsWith("https://")) { Toast.makeText(this, "সঠিক https:// URL দিন", Toast.LENGTH_LONG).show(); return; }
                prefs.edit().putString("nexora_admin_url", value).apply(); openWeb(value);
            }));
            page.addView(button("Nexora Solutions", () -> openWeb("https://nexora-international-technology.vercel.app/solutions")));
            page.addView(button("Nexora Client Portal", () -> openWeb("https://nexora-international-technology.vercel.app/client-portal")));
        }
        page.addView(text("সোশ্যাল মিডিয়া ও API সংযোগ এই সংস্করণে সরাসরি অনুমোদিত নয়; সংশ্লিষ্ট সাইটের নিজস্ব নিরাপদ সংযোগ ব্যবহার করুন।", 13, false));
        base(page);
    }
    private void openWeb(String url) {
        LinearLayout page = column();
        page.addView(button("← ড্যাশবোর্ডে ফিরুন", () -> { if (web != null) { web.destroy(); web = null; } showHome(); }));
        web = new WebView(this);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setSupportMultipleWindows(false);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        page.addView(web, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));
        base(page); web.loadUrl(url);
    }
    @Override public void onBackPressed() {
        if (web != null && web.canGoBack()) { web.goBack(); return; }
        if (web != null) { web.destroy(); web = null; }
        showHome();
    }
    @Override protected void onDestroy() { if (web != null) web.destroy(); super.onDestroy(); }
}
