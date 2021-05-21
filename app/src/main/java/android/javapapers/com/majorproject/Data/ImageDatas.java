package android.javapapers.com.majorproject.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ImageDatas {
    private ArrayList<String> mostpopularimageurls =new ArrayList<>();
    Context context;

    public void ImageDatas(Context context){
        this.context=context;
    }

    public ArrayList<String> getMostpopularimageurls() {
        mostpopularimageurls.add("https://www.jagranimages.com/images/newimg/29112019/29_11_2019-aadhar_19800047.jpg");
        mostpopularimageurls.add("https://i.pinimg.com/170x/f0/1e/a3/f01ea3f426e8e8c5a20e47c29f23a4f1.jpg");
        mostpopularimageurls.add("https://www.cars24.com/blog/wp-content/uploads/2020/05/registration.jpg");
        mostpopularimageurls.add("https://i.pinimg.com/474x/20/cf/d8/20cfd827d72e0a976427a92df0e43ce5.jpg");
        mostpopularimageurls.add("https://sarkaariservice.in/wp-content/uploads/2020/01/10th-SSC-Result-2020-696x418.jpg");
        mostpopularimageurls.add("https://images.financialexpress.com/2020/05/ration-card660-620x413.jpg");

        return mostpopularimageurls;
    }

    private ArrayList<String> centralgovernment =new ArrayList<>();
    public ArrayList<String> getCentralgovernment() {
        centralgovernment.add("https://uidai.gov.in/templates/tjbase/static/media/aadharlogo.png");
        centralgovernment.add("https://i.pinimg.com/originals/b0/a8/80/b0a88013974ffead52cbea93d933cef3.jpg");
        centralgovernment.add("https://knnindia.co.in/uploads/newsfiles/ITD-11-1-17.png");
        centralgovernment.add("https://upload.wikimedia.org/wikipedia/en/b/b9/CBSE_Logo.jpg");
        centralgovernment.add("https://www.eqmagpro.com/wp-content/uploads/2018/12/indianoil_logo-1.jpg");
        centralgovernment.add("https://getvectorlogo.com/wp-content/uploads/2018/12/bharat-petroleum-vector-logo.png");
        return centralgovernment;
    }

    private ArrayList<String> education=new ArrayList<>();
    public ArrayList<String> getEducation(){
        education.add("https://upload.wikimedia.org/wikipedia/en/b/b9/CBSE_Logo.jpg");
        education.add("https://upload.wikimedia.org/wikipedia/en/7/79/MSBSHSE_logo.png");
        education.add("https://www.sentinelassam.com/wp-content/uploads/2019/06/up.jpg");
        education.add("https://gsms-india.ac.in/wp-content/uploads/2018/04/JSOS-10th-Board.jpg");
        education.add("https://upload.wikimedia.org/wikipedia/en/2/21/Chandigarh_Police_Logo.png");
        education.add("https://www.jntufastupdates.com/wp-content/uploads/2019/01/bihar-board.png");

        return education;
    }
}
