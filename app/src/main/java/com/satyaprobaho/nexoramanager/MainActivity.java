package com.satyaprobaho.nexoramanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        section("প্রতিষ্ঠান নির্বাচন");
        act(body,"📰  সত্যপ্রবাহ  |  SATYAPROBAHO",BLUE,()->showCompany(true));
        act(body,"✦  NEXORA INTERNATIONAL TECHNOLOGY",PURPLE,()->showCompany(false));
        EditText globalSearch=new EditText(this);styleInput(globalSearch);globalSearch.setHint("🔎  অ্যাডমিন, সংবাদ, API বা মডিউল সার্চ করুন");body.addView(globalSearch);
        act(body,"সার্চ করুন",CYAN,()->runGlobalSearch(globalSearch.getText().toString()));
        act(body,"আজকের ১২টি সংবাদ পরিচালনা",GREEN,()->showDailyNews());
        section("সিস্টেম অবস্থা");LinearLayout st=card("সংযোগ ও নিরাপত্তা","লাইভ API ডেটা শুধু অনুমোদিত সংযোগ থাকলেই দেখানো হবে।");
        status(st,"Cloudflare / Website","Website link",GREEN);status(st,"Supabase / Database","Not connected",AMBER);status(st,"GitHub Actions / Build","Not checked",AMBER);status(st,"YouTube / Facebook APIs","Not connected",AMBER);
        section("দ্রুত কাজ");LinearLayout q=row();body.addView(q);tile(q,"◈","ইন্টিগ্রেশন","সংযোগের অবস্থা",BLUE,()->showIntegrations());tile(q,"▤","API ব্যবহার","ম্যানুয়াল রেকর্ড",AMBER,()->showUsage());
        act(body,"🧩  মডিউল ও পাসওয়ার্ড টুলস",CYAN,()->showTools());act(body,"⚙  অ্যাপ সেটিংস ও Nexora Admin URL",PURPLE,()->showSettings());footer();
    }
    private void showMaster(){
        page("Master Dashboard","Satyaprobaho + Nexora | Overview");
        LinearLayout c=card("Workspace overview","দুটি প্রতিষ্ঠানের ড্যাশবোর্ড আলাদা। কোনো লাইভ পরিসংখ্যান অনুমান করে দেখানো হয় না।");
        status(c,"Satyaprobaho","Ready to open",GREEN);status(c,"Nexora","Ready to open",GREEN);status(c,"External integrations","Authorization required",AMBER);
        section("প্রতিষ্ঠান");LinearLayout r=row();body.addView(r);tile(r,"📰","সত্যপ্রবাহ","News & Media",CYAN,()->showCompany(true));tile(r,"✦","Nexora","Technology & Clients",PURPLE,()->showCompany(false));
        section("ম্যানেজমেন্ট");LinearLayout m=card("অপারেশন সেন্টার","প্রতিটি মডিউল আলাদা স্ক্রিনে খুলবে।");
        act(m,"ইন্টিগ্রেশন ও API সংযোগ",BLUE,()->showIntegrations());act(m,"API কোটা ও খরচের রেকর্ড",AMBER,()->showUsage());act(m,"মডিউল যোগ-বিয়োগ ও সার্চ",CYAN,()->showTools());act(m,"নিরাপত্তা ও অ্যাক্সেস নীতি",PURPLE,()->showSecurity());act(m,"সেটিংস",CYAN,()->showSettings());
        act(body,"← হোমে ফিরুন",TILE,()->showHome());footer();
    }
    private void showCompany(boolean satya){
        company=satya?"satya":"nexora";page(satya?"সত্যপ্রবাহ Dashboard":"Nexora Dashboard",satya?"NEWSROOM  •  PUBLISHING  •  MEDIA":"CLIENTS  •  PROJECTS  •  IT SERVICES");
        LinearLayout c=card(satya?"নিউজরুম কন্ট্রোল":"টেকনোলজি অপারেশন","অ্যাকাউন্ট: "+(satya?satyaEmail:nexoraEmail));
        c.addView(txt(satya?"বিদ্যমান নিরাপদ অ্যাডমিন প্যানেল থেকে সংবাদ প্রকাশনা পরিচালনা করুন।":"ক্লায়েন্ট, সলিউশন ও সার্ভিস ব্যবস্থাপনার জন্য Nexora পোর্টাল ব্যবহার করুন।",12,MUTED,false));
        if(satya){act(c,"সত্যপ্রবাহ Admin Panel খুলুন",BLUE,()->openWeb(satyaAdmin));act(c,"আজকের ১২টি সংবাদ: তালিকা / সম্পাদনা / ডিলিট",GREEN,()->showDailyNews());act(c,"সত্যপ্রবাহ মূল ওয়েবসাইট",CYAN,()->openWeb(satyaSite));}
        else {act(c,"Nexora Website",PURPLE,()->openWeb(nexoraSite));act(c,"Nexora Solutions",BLUE,()->openWeb(nexoraSite+"solutions"));act(c,"Nexora Client Portal",GREEN,()->openWeb(nexoraSite+"client-portal"));act(c,"Nexora Admin URL সেট করুন",AMBER,()->showSettings());}
        section("মডিউল ক্যাটালগ");LinearLayout a=row();body.addView(a);
        if(satya){tile(a,"✎","Newsroom","Editorial workflow",CYAN,()->openWeb(satyaAdmin));tile(a,"▶","Video & Social","বর্তমান সাইট",PURPLE,()->openWeb(satyaSite));}
        else {tile(a,"♙","Clients","Client portal",CYAN,()->openWeb(nexoraSite+"client-portal"));tile(a,"⌘","Solutions","Service catalog",PURPLE,()->openWeb(nexoraSite+"solutions"));}
        LinearLayout b=row();body.addView(b);tile(b,"◈","Integrations","Connection status",BLUE,()->showIntegrations());tile(b,"▤","Usage & Costs","Manual ledger",AMBER,()->showUsage());
        act(body,"← Master Dashboard",TILE,()->showMaster());footer();
    }
    private Set<String> defaultModules(){
        Set<String> d=new HashSet<>();
        Collections.addAll(d,"সত্যপ্রবাহ নিউজরুম","ভিডিও ও সোশ্যাল মিডিয়া","Nexora ক্লায়েন্ট","Nexora সলিউশন","API ইন্টিগ্রেশন","API ব্যবহার ও খরচ","অ্যাপ সেটিংস");
        return d;
    }
    private Set<String> getModules(){
        Set<String> saved=prefs.getStringSet("managed_modules",null);
        return new HashSet<>(saved==null?defaultModules():saved);
    }
    private void saveModules(Set<String> modules){prefs.edit().putStringSet("managed_modules",new HashSet<>(modules)).apply();}
    private void showTools(){
        page("Module & Password Tools","মডিউল পরিচালনা, সার্চ এবং পাসওয়ার্ড সহায়তা");
        LinearLayout modules=card("মডিউল যোগ / বিয়োগ","নতুন মডিউল যোগ করুন, অপ্রয়োজনীয় মডিউল সরান এবং সার্চ করে খুঁজুন। পরিবর্তন এই ডিভাইসে সংরক্ষিত হবে।");
        EditText search=new EditText(this);styleInput(search);search.setHint("🔎  মডিউলের নাম লিখে সার্চ করুন");search.setSingleLine(true);
        modules.addView(search);
        LinearLayout list=col();LinearLayout.LayoutParams listLp=new LinearLayout.LayoutParams(-1,-2);listLp.topMargin=dp(10);modules.addView(list,listLp);
        final Runnable[] renderRef=new Runnable[1];
        renderRef[0]=()->{
            list.removeAllViews();String q=search.getText().toString().trim().toLowerCase(java.util.Locale.ROOT);
            ArrayList<String> names=new ArrayList<>(getModules());Collections.sort(names,String.CASE_INSENSITIVE_ORDER);
            int shown=0;
            for(String name:names){if(!name.toLowerCase(java.util.Locale.ROOT).contains(q))continue;shown++;
                LinearLayout item=row();pad(item,10,8,8,8);item.setBackground(bg(TILE,12));
                TextView label=txt("▣  "+name,13,FG,true);item.addView(label,new LinearLayout.LayoutParams(0,-2,1));
                TextView remove=button("− সরান",RED,()->new AlertDialog.Builder(this).setTitle("মডিউল সরাবেন?").setMessage(name+" তালিকা থেকে সরানো হবে।").setNegativeButton("বাতিল",null).setPositiveButton("সরান",(d,w)->{Set<String> m=getModules();m.remove(name);saveModules(m);renderRef[0].run();}).show());
                LinearLayout.LayoutParams rp=new LinearLayout.LayoutParams(-2,-2);item.addView(remove,rp);
                LinearLayout.LayoutParams ip=new LinearLayout.LayoutParams(-1,-2);ip.bottomMargin=dp(6);list.addView(item,ip);
            }
            if(shown==0)list.addView(txt("কোনো মডিউল পাওয়া যায়নি।",12,MUTED,false));
        };
        search.addTextChangedListener(new TextWatcher(){public void beforeTextChanged(CharSequence s,int st,int c,int a){}public void onTextChanged(CharSequence s,int st,int before,int count){renderRef[0].run();}public void afterTextChanged(Editable e){}});
        act(modules,"＋ নতুন মডিউল যোগ করুন",BLUE,()->{
            EditText name=new EditText(this);styleInput(name);name.setHint("মডিউলের নাম");
            new AlertDialog.Builder(this).setTitle("নতুন মডিউল").setView(name).setNegativeButton("বাতিল",null).setPositiveButton("যোগ করুন",(d,w)->{
                String n=name.getText().toString().trim();if(n.isEmpty()){toast("মডিউলের নাম লিখুন");return;}
                Set<String> m=getModules();m.add(n);saveModules(m);search.setText("");renderRef[0].run();toast("মডিউল যোগ হয়েছে");
            }).show();
        });
        renderRef[0].run();

        LinearLayout pass=card("পাসওয়ার্ড Show / Hide","পাসওয়ার্ড লিখুন, চাইলে দৃশ্যমান করুন অথবা চার অক্ষরের অস্থায়ী কোড তৈরি করুন।");
        LinearLayout inputRow=row();
        EditText password=new EditText(this);styleInput(password);password.setHint("পাসওয়ার্ড লিখুন");password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputRow.addView(password,new LinearLayout.LayoutParams(0,-2,1));
        TextView toggle=button("SHOW",PURPLE,()->{
            boolean hidden=(password.getInputType()&(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_NUMBER_VARIATION_PASSWORD))!=0;
            password.setInputType(InputType.TYPE_CLASS_TEXT|(hidden?InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:InputType.TYPE_TEXT_VARIATION_PASSWORD));
            password.setSelection(password.getText().length());
        });
        inputRow.addView(toggle,new LinearLayout.LayoutParams(-2,-2));pass.addView(inputRow);
        // The toggle label and input visibility are updated together.
        toggle.setOnClickListener(v->{
            boolean hidden=(password.getInputType()&InputType.TYPE_TEXT_VARIATION_PASSWORD)==InputType.TYPE_TEXT_VARIATION_PASSWORD;
            password.setInputType(InputType.TYPE_CLASS_TEXT|(hidden?InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:InputType.TYPE_TEXT_VARIATION_PASSWORD));
            password.setSelection(password.getText().length());toggle.setText(hidden?"HIDE":"SHOW");
        });
        act(pass,"✦  ৪ অক্ষরের কোড তৈরি করুন",CYAN,()->{
            final String chars="ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789";
            SecureRandom random=new SecureRandom();StringBuilder code=new StringBuilder();
            for(int i=0;i<4;i++)code.append(chars.charAt(random.nextInt(chars.length())));
            password.setText(code.toString());password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);toggle.setText("HIDE");
        });
        act(pass,"পাসওয়ার্ড কপি করুন",BLUE,()->{
            String value=password.getText().toString();if(value.isEmpty()){toast("আগে পাসওয়ার্ড লিখুন বা তৈরি করুন");return;}
            android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(android.content.ClipData.newPlainText("Password",value));toast("কপি হয়েছে");
        });
        TextView warning=txt("নিরাপত্তা নোট: চার অক্ষরের কোড কেবল অস্থায়ী PIN/টেস্টের জন্য। ইমেইল, অ্যাডমিন বা গুরুত্বপূর্ণ অ্যাকাউন্টে শক্তিশালী দীর্ঘ পাসওয়ার্ড ব্যবহার করুন। এই টুলে পাসওয়ার্ড সংরক্ষণ করা হয় না।",11,AMBER,false);
        warning.setPadding(0,dp(8),0,0);pass.addView(warning);
        act(body,"← হোমে ফিরুন",TILE,()->showHome());footer();
    }
    private void runGlobalSearch(String query){
        String q=query==null?"":query.trim().toLowerCase(Locale.ROOT);
        if(q.isEmpty()){toast("সার্চ করার জন্য কিছু লিখুন");return;}
        if(q.contains("সংবাদ")||q.contains("news")||q.contains("পত্রিকা")){showDailyNews();return;}
        if(q.contains("পাস")||q.contains("password")||q.contains("module")||q.contains("মডিউল")){showTools();return;}
        if(q.contains("api")||q.contains("quota")||q.contains("খরচ")||q.contains("মেয়াদ")){showUsage();return;}
        if(q.contains("nexora")||q.contains("ইন্টারন্যাশনাল")){showCompany(false);return;}
        if(q.contains("সত্য")||q.contains("satyaprobaho")){showCompany(true);return;}
        toast("মিল পাওয়া যায়নি। সংবাদ, API, পাসওয়ার্ড, মডিউল বা প্রতিষ্ঠানের নাম লিখুন।");
    }
    private String todayKey(){return new SimpleDateFormat("yyyyMMdd",Locale.US).format(new Date());}
    private void showDailyNews(){
        company="satya";page("দৈনিক ১২ সংবাদ","প্রতিদিনের প্রকাশনা তালিকা • ডিভাইসে সংরক্ষিত ট্র্যাকার");
        LinearLayout c=card("সত্যপ্রবাহ Admin Panel","লাইভ ওয়েবসাইটের প্রকৃত সংবাদ সম্পাদনা ও মুছে ফেলার জন্য মূল অ্যাডমিন প্যানেল খুলুন।");
        act(c,"ওয়েবসাইটের আসল সংবাদ সম্পাদনা / ডিলিট",BLUE,()->openWeb(satyaAdmin));
        LinearLayout tracker=card("আজকের ১২টি সংবাদ ট্র্যাকার","এখানে শিরোনাম ও লিংক এই ফোনে সংরক্ষিত হবে; এই ট্র্যাকার পরিবর্তন করলে লাইভ ওয়েবসাইটের সংবাদ বদলাবে না।");
        for(int i=1;i<=12;i++){
            final int slot=i;String key="daily_"+todayKey()+"_"+slot;
            String title=prefs.getString(key+"_title","");String url=prefs.getString(key+"_url","");
            LinearLayout item=col();pad(item,11,10,11,10);item.setBackground(bg(TILE,13));
            item.addView(txt(String.format(Locale.US,"%02d. ",slot)+(title.isEmpty()?"সংবাদ যোগ করা হয়নি":title),14,FG,true));
            if(!url.isEmpty())item.addView(txt(url,11,MUTED,false));
            LinearLayout actions=row();
            TextView edit=button(title.isEmpty()?"＋ যোগ করুন":"✎ সম্পাদনা",BLUE,()->newsDialog(slot));
            TextView del=button("ডিলিট",RED,()->new AlertDialog.Builder(this).setTitle("এই এন্ট্রি মুছবেন?").setMessage("শুধু এই ফোনের আজকের ট্র্যাকার থেকে মুছবে।").setNegativeButton("না",null).setPositiveButton("মুছুন",(d,w)->{prefs.edit().remove(key+"_title").remove(key+"_url").apply();showDailyNews();}).show());
            LinearLayout.LayoutParams ap=new LinearLayout.LayoutParams(0,-2,1);ap.setMargins(0,dp(7),dp(5),0);actions.addView(edit,ap);
            LinearLayout.LayoutParams bp=new LinearLayout.LayoutParams(0,-2,1);bp.setMargins(dp(5),dp(7),0,0);actions.addView(del,bp);
            item.addView(actions);LinearLayout.LayoutParams ip=new LinearLayout.LayoutParams(-1,-2);ip.bottomMargin=dp(7);tracker.addView(item,ip);
        }
        act(body,"← সত্যপ্রবাহ ড্যাশবোর্ড",TILE,()->showCompany(true));footer();
    }
    private void newsDialog(int slot){
        String key="daily_"+todayKey()+"_"+slot;LinearLayout form=col();pad(form,18,8,18,4);
        EditText title=new EditText(this);styleInput(title);title.setHint("সংবাদের শিরোনাম");title.setText(prefs.getString(key+"_title",""));form.addView(title);
        EditText url=new EditText(this);styleInput(url);url.setHint("সংবাদ URL (ঐচ্ছিক)");url.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_URI);url.setText(prefs.getString(key+"_url",""));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.topMargin=dp(9);form.addView(url,lp);
        AlertDialog dlg=new AlertDialog.Builder(this).setTitle("সংবাদ "+slot+" সম্পাদনা").setView(form).setNegativeButton("বাতিল",null).setPositiveButton("সংরক্ষণ",null).create();
        dlg.setOnShowListener(x->{dlg.getButton(-1).setOnClickListener(v->{String t=title.getText().toString().trim(),u=url.getText().toString().trim();if(t.isEmpty()){toast("সংবাদের শিরোনাম লিখুন");return;}if(!u.isEmpty()&&!validHttps(u)){toast("URL অবশ্যই https:// দিয়ে শুরু হতে হবে");return;}prefs.edit().putString(key+"_title",t).putString(key+"_url",u).apply();dlg.dismiss();showDailyNews();});});dlg.show();
    }
    private void showIntegrations(){
        page("Integration Center","সংযোগের অবস্থা ও অনুমোদন");
        LinearLayout c=card("সংযোগ নীতি","API অনুমতি/credentials না পাওয়া পর্যন্ত Not connected দেখানো হবে। কোনো secret বা password অ্যাপে সংরক্ষণ করা হয় না।");
        status(c,"Cloudflare","Website link",GREEN);status(c,"Supabase","Not connected",AMBER);status(c,"GitHub","Not connected in app",AMBER);status(c,"Vercel","Not connected",AMBER);status(c,"Google / YouTube API","Not connected",AMBER);status(c,"Meta / Facebook API","Not connected",AMBER);status(c,"Brevo SMTP","Not connected",AMBER);
        act(c,"Cloudflare Dashboard",BLUE,()->openWeb("https://dash.cloudflare.com/"));act(c,"GitHub",PURPLE,()->openWeb("https://github.com/"));act(c,"Supabase Dashboard",GREEN,()->openWeb("https://supabase.com/dashboard"));act(c,"Google Cloud Console",AMBER,()->openWeb("https://console.cloud.google.com/"));act(c,"Meta for Developers",CYAN,()->openWeb("https://developers.facebook.com/"));
        act(body,"← হোমে ফিরুন",TILE,()->showHome());footer();
    }
    private void showUsage(){
        page("API Usage Ledger","কী ব্যবহার হচ্ছে • মেয়াদ • বাকি অ্যাকশন");
        LinearLayout c=card("ব্যবহার ও মেয়াদের বিস্তারিত","প্রতিটি সেবার প্যাকেজ, মোট কোটা, ব্যবহৃত অ্যাকশন এবং মেয়াদ লিখুন। এগুলো ম্যানুয়াল হিসাব—provider API থেকে লাইভ ডেটা নয়।");
        String[] ps={"Cloudflare","Supabase","GitHub Actions","Vercel","YouTube API","Facebook Graph API","Brevo","Other"};
        for(String p:ps){
            String v=prefs.getString("usage_"+p,"");String days=prefs.getString("usage_"+p+"_days","");String left=prefs.getString("usage_"+p+"_left","");
            status(c,p,v.isEmpty()?"রেকর্ড নেই":v,MUTED);
            if(!days.isEmpty())status(c,"মেয়াদ / দিন বাকি",days,CYAN);
            if(!left.isEmpty())status(c,"অ্যাকশন বাকি",left,GREEN);
        }
        act(c,"＋ সেবা / প্যাকেজের বিস্তারিত যোগ বা আপডেট",AMBER,()->usageDialog());
        act(c,"সব ম্যানুয়াল রেকর্ড মুছুন",RED,()->new AlertDialog.Builder(this).setTitle("রেকর্ড মুছবেন?").setMessage("শুধু এই ডিভাইসের ম্যানুয়াল রেকর্ড মুছে যাবে।").setNegativeButton("বাতিল",null).setPositiveButton("মুছুন",(d,w)->{for(String p:ps)prefs.edit().remove("usage_"+p).remove("usage_"+p+"_days").remove("usage_"+p+"_left").apply();showUsage();}).show());
        act(body,"← হোমে ফিরুন",TILE,()->showHome());footer();
    }
    private void styleInput(EditText e){e.setTextColor(FG);e.setHintTextColor(MUTED);e.setSingleLine(true);e.setBackground(bg(TILE,12));pad(e,12,10,12,10);}
    private void usageDialog(){
        LinearLayout form=col();pad(form,18,8,18,4);
        EditText p=new EditText(this);p.setHint("সেবা / Provider নাম");styleInput(p);form.addView(p);
        EditText plan=new EditText(this);plan.setHint("প্যাকেজ / কী কী ব্যবহার হচ্ছে");styleInput(plan);LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.topMargin=dp(8);form.addView(plan,lp);
        EditText total=new EditText(this);total.setHint("মোট অ্যাকশন / কোটা");total.setInputType(InputType.TYPE_CLASS_NUMBER);styleInput(total);lp=new LinearLayout.LayoutParams(-1,-2);lp.topMargin=dp(8);form.addView(total,lp);
        EditText used=new EditText(this);used.setHint("ব্যবহৃত অ্যাকশন");used.setInputType(InputType.TYPE_CLASS_NUMBER);styleInput(used);lp=new LinearLayout.LayoutParams(-1,-2);lp.topMargin=dp(8);form.addView(used,lp);
        EditText days=new EditText(this);days.setHint("মেয়াদ শেষ হতে দিন বাকি");days.setInputType(InputType.TYPE_CLASS_NUMBER);styleInput(days);lp=new LinearLayout.LayoutParams(-1,-2);lp.topMargin=dp(8);form.addView(days,lp);
        AlertDialog dlg=new AlertDialog.Builder(this).setTitle("সেবা / ব্যবহার বিবরণ").setView(form).setNegativeButton("বাতিল",null).setPositiveButton("সংরক্ষণ",null).create();
        dlg.setOnShowListener(x->{dlg.getButton(-1).setOnClickListener(v->{
            String key=p.getText().toString().trim(),pl=plan.getText().toString().trim();
            if(key.isEmpty()||pl.isEmpty()||total.getText().toString().trim().isEmpty()||used.getText().toString().trim().isEmpty()||days.getText().toString().trim().isEmpty()){toast("সব ঘর পূরণ করুন");return;}
            try{int t=Integer.parseInt(total.getText().toString()),u=Integer.parseInt(used.getText().toString()),d=Integer.parseInt(days.getText().toString());if(t<0||u<0||d<0||u>t){toast("সংখ্যা সঠিকভাবে দিন; ব্যবহৃত অ্যাকশন মোট কোটার বেশি হতে পারবে না");return;}
                prefs.edit().putString("usage_"+key,pl+" | মোট: "+t+" | ব্যবহৃত: "+u).putString("usage_"+key+"_days",d+" দিন").putString("usage_"+key+"_left",(t-u)+" অ্যাকশন").apply();dlg.dismiss();showUsage();
            }catch(Exception e){toast("সংখ্যা সঠিকভাবে লিখুন");}
        });});dlg.show();
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
        if(!validHttps(url)){toast("শুধু সঠিক HTTPS ঠিকানা খোলা যাবে");return;}
        if(web!=null){web.stopLoading();web.destroy();web=null;}
        LinearLayout root=col();root.setBackgroundColor(BG);
        LinearLayout bar=row();pad(bar,12,8,12,8);bar.setBackgroundColor(Color.rgb(13,19,43));
        TextView back=button("← Dashboard",TILE,()->{if(web!=null){web.stopLoading();web.destroy();web=null;}if(company.equals("satya"))showCompany(true);else if(company.equals("nexora"))showCompany(false);else showHome();});
        bar.addView(back,new LinearLayout.LayoutParams(-2,-2));
        bar.addView(txt("  Secure Web Workspace",14,FG,true),new LinearLayout.LayoutParams(0,-2,1));
        root.addView(bar,new LinearLayout.LayoutParams(-1,-2));
        web=new WebView(this);web.setBackgroundColor(BG);
        web.setVerticalScrollBarEnabled(true);web.setHorizontalScrollBarEnabled(false);
        web.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        android.webkit.WebSettings settings=web.getSettings();
        settings.setJavaScriptEnabled(true);settings.setDomStorageEnabled(true);settings.setDatabaseEnabled(true);
        settings.setLoadWithOverviewMode(true);settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);settings.setBuiltInZoomControls(true);settings.setDisplayZoomControls(false);
        settings.setDefaultTextEncodingName("UTF-8");settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setSupportMultipleWindows(false);settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setAllowFileAccess(false);settings.setAllowContentAccess(false);
        android.webkit.CookieManager cookies=android.webkit.CookieManager.getInstance();
        cookies.setAcceptCookie(true);cookies.setAcceptThirdPartyCookies(web,true);
        web.setWebViewClient(new WebViewClient(){
            @Override public boolean shouldOverrideUrlLoading(WebView v,String next){
                if(validHttps(next))return false;
                toast("নিরাপত্তার জন্য এই লিংকটি ব্লক করা হয়েছে");return true;
            }
            @Override public void onPageFinished(WebView v,String loaded){super.onPageFinished(v,loaded);cookies.flush();}
        });
        web.setWebChromeClient(new WebChromeClient());
        root.addView(web,new LinearLayout.LayoutParams(-1,0,1));
        setContentView(root);
        getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        web.loadUrl(url);
    }
    private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_LONG).show();}
    @Override public void onBackPressed(){if(web!=null&&web.canGoBack()){web.goBack();return;}if(web!=null){web.destroy();web=null;}if(company.equals("satya"))showCompany(true);else if(company.equals("nexora"))showCompany(false);else showHome();}
    @Override protected void onDestroy(){if(web!=null)web.destroy();super.onDestroy();}
}