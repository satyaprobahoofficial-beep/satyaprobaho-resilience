package com.satyaprobaho.nexoramanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URI;

public class MainActivity extends Activity {
    private static final int BG=Color.rgb(8,12,29), CARD=Color.rgb(17,25,49), TILE=Color.rgb(24,35,66);
    private static final int FG=Color.rgb(235,241,255), MUTED=Color.rgb(151,166,198);
    private static final int BLUE=Color.rgb(54,132,255), PURPLE=Color.rgb(139,92,246), CYAN=Color.rgb(34,211,238);
    private static final int GREEN=Color.rgb(16,185,129), AMBER=Color.rgb(245,158,11), RED=Color.rgb(248,113,113);
    private LinearLayout body;
    private WebView web;
    private SharedPreferences prefs;
    private String company="master";
    private final String satyaAdmin="https://satyaprobaho.satyaprobaho.workers.dev/admin.html";
    private final String satyaSite="https://satyaprobaho.satyaprobaho.workers.dev/";
    private final String nexoraSite="https://nexora-international-technology.vercel.app/";
    private final String satyaEmail="satyaprobaho.official@gmail.com";
    private final String nexoraEmail="computer26training@gmail.com";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(BG); getWindow().setNavigationBarColor(BG);
        prefs=getSharedPreferences("manager_settings",MODE_PRIVATE); showHome();
    }
    private int dp(float n){return (int)(n*getResources().getDisplayMetrics().density+0.5f);}
    private GradientDrawable bg(int color,int radius){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(dp(radius));d.setStroke(dp(1),Color.argb(55,130,160,220));return d;}
    private GradientDrawable grad(int a,int b,int radius){GradientDrawable d=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{a,b});d.setCornerRadius(dp(radius));return d;}
    private LinearLayout col(){LinearLayout l=new LinearLayout(this);l.setOrientation(1);return l;}
    private LinearLayout row(){LinearLayout l=new LinearLayout(this);l.setOrientation(0);l.setGravity(Gravity.CENTER_VERTICAL);return l;}
    private void pad(View v,int a,int b,int c,int d){v.setPadding(dp(a),dp(b),dp(c),dp(d));}
    private TextView txt(String s,int size,int color,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setGravity(Gravity.CENTER_VERTICAL);if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);return t;}
    private void page(String title,String sub){
        LinearLayout root=col();root.setBackgroundColor(BG);setContentView(root);
        LinearLayout head=col();pad(head,20,15,20,14);head.setBackground(grad(Color.rgb(20,35,76),Color.rgb(13,19,43),0));
        head.addView(txt("✦  NEXORA  ×  সত্যপ্রবাহ",12,CYAN,true));
        TextView h=txt(title,24,FG,true);h.setPadding(0,dp(7),0,0);head.addView(h);head.addView(txt(sub,12,MUTED,false));root.addView(head);
        ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);body=col();pad(body,16,14,16,28);scroll.addView(body);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
    }
    private LinearLayout card(String title,String desc){
        LinearLayout c=col();pad(c,15,14,15,14);c.setBackground(bg(CARD,19));c.addView(txt(title,16,FG,true));
        if(desc!=null&&!desc.isEmpty()){TextView d=txt(desc,12,MUTED,false);d.setPadding(0,dp(6),0,0);c.addView(d);}
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=dp(10);body.addView(c,p);return c;
    }
    private int darken(int c){return Color.rgb((int)(Color.red(c)*.75),(int)(Color.green(c)*.75),(int)(Color.blue(c)*.75));}
    private TextView button(String s,int color,Runnable action){TextView b=txt(s,14,Color.WHITE,true);b.setGravity(Gravity.CENTER);pad(b,14,13,14,13);b.setBackground(grad(color,darken(color),15));b.setElevation(dp(3));b.setOnClickListener(v->action.run());return b;}
    private void act(LinearLayout p,String s,int c,Runnable r){LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.topMargin=dp(8);p.addView(button(s,c,r),lp);}
    private void section(String s){TextView t=txt(s,15,FG,true);t.setPadding(dp(2),dp(13),0,dp(8));body.addView(t);}
    private void tile(LinearLayout p,String icon,String title,String desc,int color,Runnable r){
        LinearLayout t=col();pad(t,12,13,12,13);t.setBackground(bg(TILE,16));t.addView(txt(icon,22,color,true));TextView h=txt(title,14,FG,true);h.setPadding(0,dp(7),0,0);t.addView(h);TextView d=txt(desc,11,MUTED,false);d.setPadding(0,dp(4),0,0);t.addView(d);t.setOnClickListener(v->r.run());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,-2,1);lp.setMargins(dp(4),dp(4),dp(4),dp(4));p.addView(t,lp);
    }
    private void status(LinearLayout p,String name,String value,int color){LinearLayout r=row();r.addView(txt(name,13,FG,false),new LinearLayout.LayoutParams(0,-2,1));r.addView(txt("● "+value,12,color,true));p.addView(r);View line=new View(this);line.setBackgroundColor(Color.rgb(39,52,83));LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(1));lp.setMargins(0,dp(9),0,dp(9));p.addView(line,lp);}
    private void footer(){TextView f=txt("SECURE WORKSPACE  •  DARK UI  •  v1.1",10,MUTED,true);f.setGravity(Gravity.CENTER);f.setPadding(0,dp(20),0,dp(8));body.addView(f);}
    private void showHome(){
        company="master";page("Command Center","দুই প্রতিষ্ঠানের নিরাপদ, একীভূত ব্যবস্থাপনা");
        LinearLayout hero=card("আপনার ব্যবসার কন্ট্রোল রুম","Satyaprobaho  •  Nexora International Technology");hero.setBackground(grad(Color.rgb(22,48,94),Color.rgb(49,35,99),20));
        hero.addView(txt("01  MASTER WORKSPACE",10,CYAN,true));act(hero,"মাস্টার ড্যাশবোর্ড খুলুন",BLUE,()->showMaster());
        section("প্রতিষ্ঠান নির্বাচন");LinearLayout r=row();body.addView(r);tile(r,"📰","সত্যপ্রবাহ","নিউজরুম ও প্রকাশনা",CYAN,()->showCompany(true));tile(r,"✦","Nexora","IT ও ক্লায়েন্ট অপারেশন",PURPLE,()->showCompany(false));
        section("সিস্টেম অবস্থা");LinearLayout st=card("সংযোগ ও নিরাপত্তা","লাইভ API ডেটা শুধু অনুমোদিত সংযোগ থাকলেই দেখানো হবে।");
        status(st,"Cloudflare / Website","Website link",GREEN);status(st,"Supabase / Database","Not connected",AMBER);status(st,"GitHub Actions / Build","Not checked",AMBER);status(st,"YouTube / Facebook APIs","Not connected",AMBER);
        section("দ্রুত কাজ");LinearLayout q=row();body.addView(q);tile(q,"◈","ইন্টিগ্রেশন","সংযোগের অবস্থা",BLUE,()->showIntegrations());tile(q,"▤","API ব্যবহার","ম্যানুয়াল রেকর্ড",AMBER,()->showUsage());
        act(body,"⚙  অ্যাপ সেটিংস ও Nexora Admin URL",PURPLE,()->showSettings());footer();
    }
    private void showMaster(){
        page("Master Dashboard","Satyaprobaho + Nexora | Overview");
        LinearLayout c=card("Workspace overview","দুটি প্রতিষ্ঠানের ড্যাশবোর্ড আলাদা। কোনো লাইভ পরিসংখ্যান অনুমান করে দেখানো হয় না।");
        status(c,"Satyaprobaho","Ready to open",GREEN);status(c,"Nexora","Ready to open",GREEN);status(c,"External integrations","Authorization required",AMBER);
        section("প্রতিষ্ঠান");LinearLayout r=row();body.addView(r);tile(r,"📰","সত্যপ্রবাহ","News & Media",CYAN,()->showCompany(true));tile(r,"✦","Nexora","Technology & Clients",PURPLE,()->showCompany(false));
        section("ম্যানেজমেন্ট");LinearLayout m=card("অপারেশন সেন্টার","প্রতিটি মডিউল আলাদা স্ক্রিনে খুলবে।");
        act(m,"ইন্টিগ্রেশন ও API সংযোগ",BLUE,()->showIntegrations());act(m,"API কোটা ও খরচের রেকর্ড",AMBER,()->showUsage());act(m,"নিরাপত্তা ও অ্যাক্সেস নীতি",PURPLE,()->showSecurity());act(m,"সেটিংস",CYAN,()->showSettings());
        act(body,"← হোমে ফিরুন",TILE,()->showHome());footer();
    }
    private void showCompany(boolean satya){
        company=satya?"satya":"nexora";page(satya?"সত্যপ্রবাহ Dashboard":"Nexora Dashboard",satya?"NEWSROOM  •  PUBLISHING  •  MEDIA":"CLIENTS  •  PROJECTS  •  IT SERVICES");
        LinearLayout c=card(satya?"নিউজরুম কন্ট্রোল":"টেকনোলজি অপারেশন","অ্যাকাউন্ট: "+(satya?satyaEmail:nexoraEmail));
        c.addView(txt(satya?"বিদ্যমান নিরাপদ অ্যাডমিন প্যানেল থেকে সংবাদ প্রকাশনা পরিচালনা করুন।":"ক্লায়েন্ট, সলিউশন ও সার্ভিস ব্যবস্থাপনার জন্য Nexora পোর্টাল ব্যবহার করুন।",12,MUTED,false));
        if(satya){act(c,"সত্যপ্রবাহ Admin Panel খুলুন",BLUE,()->openWeb(satyaAdmin));act(c,"সত্যপ্রবাহ মূল ওয়েবসাইট",CYAN,()->openWeb(satyaSite));}
        else {act(c,"Nexora Website",PURPLE,()->openWeb(nexoraSite));act(c,"Nexora Solutions",BLUE,()->openWeb(nexoraSite+"solutions"));act(c,"Nexora Client Portal",GREEN,()->openWeb(nexoraSite+"client-portal"));act(c,"Nexora Admin URL সেট করুন",AMBER,()->showSettings());}
        section("মডিউল ক্যাটালগ");LinearLayout a=row();body.addView(a);
        if(satya){tile(a,"✎","Newsroom","Editorial workflow",CYAN,()->openWeb(satyaAdmin));tile(a,"▶","Video & Social","বর্তমান সাইট",PURPLE,()->openWeb(satyaSite));}
        else {tile(a,"♙","Clients","Client portal",CYAN,()->openWeb(nexoraSite+"client-portal"));tile(a,"⌘","Solutions","Service catalog",PURPLE,()->openWeb(nexoraSite+"solutions"));}
        LinearLayout b=row();body.addView(b);tile(b,"◈","Integrations","Connection status",BLUE,()->showIntegrations());tile(b,"▤","Usage & Costs","Manual ledger",AMBER,()->showUsage());
        act(body,"← Master Dashboard",TILE,()->showMaster());footer();
    }
    private void showIntegrations(){
        page("Integration Center","সংযোগের অবস্থা ও অনুমোদন");
        LinearLayout c=card("সংযোগ নীতি","API অনুমতি/credentials না পাওয়া পর্যন্ত Not connected দেখানো হবে। কোনো secret বা password অ্যাপে সংরক্ষণ করা হয় না।");
        status(c,"Cloudflare","Website link",GREEN);status(c,"Supabase","Not connected",AMBER);status(c,"GitHub","Not connected in app",AMBER);status(c,"Vercel","Not connected",AMBER);status(c,"Google / YouTube API","Not connected",AMBER);status(c,"Meta / Facebook API","Not connected",AMBER);status(c,"Brevo SMTP","Not connected",AMBER);
        act(c,"Cloudflare Dashboard",BLUE,()->openWeb("https://dash.cloudflare.com/"));act(c,"GitHub",PURPLE,()->openWeb("https://github.com/"));act(c,"Supabase Dashboard",GREEN,()->openWeb("https://supabase.com/dashboard"));act(c,"Google Cloud Console",AMBER,()->openWeb("https://console.cloud.google.com/"));act(c,"Meta for Developers",CYAN,()->openWeb("https://developers.facebook.com/"));
        act(body,"← হোমে ফিরুন",TILE,()->showHome());footer();
    }
    private void showUsage(){
        page("API Usage Ledger","কোটা, খরচ ও মেয়াদ | Manual records");
        LinearLayout c=card("স্বচ্ছ হিসাব","Provider থেকে live quota এখনো পড়া হচ্ছে না। এই তালিকার তথ্যগুলো ম্যানুয়াল রেকর্ড, live usage নয়।");
        String[] ps={"Cloudflare","Supabase","GitHub Actions","Vercel","YouTube API","Facebook Graph API","Brevo","Other"};
        for(String p:ps){String v=prefs.getString("usage_"+p,"কোনো রেকর্ড নেই");status(c,p,v,v.equals("কোনো রেকর্ড নেই")?MUTED:CYAN);}
        act(c,"＋ নতুন ম্যানুয়াল রেকর্ড",AMBER,()->usageDialog());
        act(c,"সব ম্যানুয়াল রেকর্ড মুছুন",RED,()->new AlertDialog.Builder(this).setTitle("রেকর্ড মুছবেন?").setMessage("শুধু এই ডিভাইসে রাখা ম্যানুয়াল রেকর্ড মুছে যাবে।").setNegativeButton("বাতিল",null).setPositiveButton("মুছুন",(d,w)->{for(String p:ps)prefs.edit().remove("usage_"+p).apply();showUsage();}).show());
        act(body,"← হোমে ফিরুন",TILE,()->showHome());footer();
    }
    private void styleInput(EditText e){e.setTextColor(FG);e.setHintTextColor(MUTED);e.setSingleLine(true);e.setBackground(bg(TILE,12));pad(e,12,10,12,10);}
    private void usageDialog(){
        LinearLayout form=col();pad(form,18,8,18,4);EditText p=new EditText(this);p.setHint("Provider নাম");styleInput(p);form.addView(p);
        EditText d=new EditText(this);d.setHint("Usage / quota / cost / expiry");styleInput(d);LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.topMargin=dp(10);form.addView(d,lp);
        AlertDialog dlg=new AlertDialog.Builder(this).setTitle("Manual usage record").setView(form).setNegativeButton("বাতিল",null).setPositiveButton("সংরক্ষণ",null).create();
        dlg.setOnShowListener(x->{dlg.getWindow().setBackgroundDrawable(bg(CARD,18));dlg.getButton(-1).setTextColor(CYAN);dlg.getButton(-2).setTextColor(MUTED);dlg.getButton(-1).setOnClickListener(v->{String key=p.getText().toString().trim(),val=d.getText().toString().trim();if(key.isEmpty()||val.isEmpty()){toast("Provider ও তথ্য লিখুন");return;}prefs.edit().putString("usage_"+key,val).apply();dlg.dismiss();showUsage();});});dlg.show();
    }
    private void showSettings(){
        page("Workspace Settings","ডিভাইস-ভিত্তিক অ্যাপ কনফিগারেশন");
        LinearLayout c=card("Nexora Admin URL","যাচাইকৃত HTTPS admin address সংরক্ষণ করুন। ডিফল্ট মূল ওয়েবসাইট; এটি admin endpoint বলে নিশ্চিত নয়।");
        EditText url=new EditText(this);styleInput(url);url.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_URI);url.setText(prefs.getString("nexora_admin_url",nexoraSite));c.addView(url);
        act(c,"URL যাচাই করে সংরক্ষণ",PURPLE,()->{String v=url.getText().toString().trim();if(!validHttps(v)){toast("সঠিক HTTPS URL দিন");return;}prefs.edit().putString("nexora_admin_url",v).apply();toast("URL এই ডিভাইসে সংরক্ষিত হয়েছে");});
        act(c,"সংরক্ষিত URL খুলুন",BLUE,()->{String v=url.getText().toString().trim();if(validHttps(v)){prefs.edit().putString("nexora_admin_url",v).apply();openWeb(v);}else toast("সঠিক HTTPS URL দিন");});
        LinearLayout s=card("অ্যাকাউন্ট ও ডেটা সুরক্ষা","কোনো password, API token বা payment credential অ্যাপে রাখা হয় না।");act(s,"নিরাপত্তা নীতি দেখুন",GREEN,()->showSecurity());
        act(body,"← হোমে ফিরুন",TILE,()->showHome());footer();
    }
    private void showSecurity(){
        page("Security & Privacy","নিরাপদ অ্যাক্সেসের নীতি");
        LinearLayout c=card("এই অ্যাপের নিরাপত্তা সীমা","বিদ্যমান ওয়েবসাইটের নিজস্ব authentication ব্যবহার করুন।");
        status(c,"Passwords / API secrets","Not stored",GREEN);status(c,"Nexora admin address","Device preference",CYAN);status(c,"Usage ledger","Local manual data",AMBER);status(c,"Organization entry","Separate dashboards",GREEN);
        c.addView(txt("প্রকৃত multi-user roles, server-side audit log, encrypted secret vault, cloud backup ও live provider quota চালু করতে সংশ্লিষ্ট backend এবং অনুমোদিত API integration প্রয়োজন। এই সংস্করণে এগুলো চালু আছে বলে দাবি করা হচ্ছে না।",12,MUTED,false));
        act(body,"← হোমে ফিরুন",TILE,()->showHome());footer();
    }
    private boolean validHttps(String s){try{URI u=URI.create(s);return "https".equalsIgnoreCase(u.getScheme())&&u.getHost()!=null;}catch(Exception e){return false;}}
    private void openWeb(String url){
        if(!validHttps(url)){toast("শুধু সঠিক HTTPS ঠিকানা খোলা যাবে");return;}if(web!=null){web.destroy();web=null;}
        page("Secure Web Workspace","HTTPS connection");act(body,"← ড্যাশবোর্ডে ফিরুন",TILE,()->{if(web!=null){web.destroy();web=null;}if(company.equals("satya"))showCompany(true);else if(company.equals("nexora"))showCompany(false);else showHome();});
        web=new WebView(this);web.setBackgroundColor(BG);web.getSettings().setJavaScriptEnabled(true);web.getSettings().setDomStorageEnabled(true);web.getSettings().setSupportMultipleWindows(false);
        web.setWebViewClient(new WebViewClient(){@Override public boolean shouldOverrideUrlLoading(WebView v,String next){if(validHttps(next))return false;toast("নিরাপত্তার জন্য লিংক ব্লক করা হয়েছে");return true;}});
        web.setWebChromeClient(new WebChromeClient());LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(560));lp.topMargin=dp(12);body.addView(web,lp);web.loadUrl(url);
    }
    private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_LONG).show();}
    @Override public void onBackPressed(){if(web!=null&&web.canGoBack()){web.goBack();return;}if(web!=null){web.destroy();web=null;}if(company.equals("satya"))showCompany(true);else if(company.equals("nexora"))showCompany(false);else showHome();}
    @Override protected void onDestroy(){if(web!=null)web.destroy();super.onDestroy();}
}