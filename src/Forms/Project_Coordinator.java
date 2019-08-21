/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author WEJE
 */
public class Project_Coordinator extends javax.swing.JFrame {

    /**
     * Creates new form Project_Coordinator
     */
Connection conn = null;
ResultSet rs = null;
PreparedStatement pst = null;
int r1= 0;
int r2= 0;
int r3= 0;
int r4= 0;
int r5= 0;

int s1= 0;
int s2= 0;
int s3= 0;
int s4= 0;
int s5= 0;
String matchResult ="";

private int N, engagedCount;

    private String[][] studentPref;

    private String[][] supervisorPref;

    private String[] student_list;

    private String[] supervisor_list;

    private String[] supervisorPartner;

    private boolean[] studentEngaged;
    
    String[] m = {"", "", "", "", ""};
    
    String[] w = {"", "", "", "", ""};
    
    
     String[][] mp = {   {"", "", "", "", ""}, 

                         {"", "", "", "", ""}, 

                         {"", "", "", "", ""}, 

                         {"", "", "", "", ""},

                         {"", "", "", "", ""}
                     };
String[][] wp = {        {"", "", "", "", ""}, 

                         {"", "", "", "", ""}, 

                         {"", "", "", "", ""},

                         {"", "", "", "", ""}, 

                         {"", "", "", "", ""}
                };


    
 /** Constructor **/

    public void Project_Coordinator(String[] m, String[] w, String[][] mp, String[][] wp)

    {

        N = mp.length;

        engagedCount = 0;
        

        student_list = m;

        supervisor_list = w;

        studentPref = mp;

        supervisorPref = wp;

        studentEngaged = new boolean[N];

        supervisorPartner = new String[N];

        calcMatches();

    }
    /** function to calculate all matches **/

    private void calcMatches()

    {

        while (engagedCount < N)

        {

            int free;

            for (free = 0; free < N; free++)

                if (!studentEngaged[free])

                    break;

 

            for (int i = 0; i < N && !studentEngaged[free]; i++)

            {

                int index = supervisorIndexOf(studentPref[free][i]);

                if (supervisorPartner[index] == null)

                {

                    supervisorPartner[index] = student_list[free];

                    studentEngaged[free] = true;

                    engagedCount++;

                }

                else

                {

                    String currentPartner = supervisorPartner[index];

                    if (morePreference(currentPartner, student_list
[free], index))

                    {

                        supervisorPartner[index] = student_list[free];

                        studentEngaged[free] = true;

                        studentEngaged[studentIndexOf(currentPartner)] = 
false;

                    }

                }

            }            

        }

        printMatchResult();

    }
    
     /** function to check if supervisor prefers new partner over old 
assigned partner **/

    private boolean morePreference(String curPartner, String 
newPartner, int index)

    {

        for (int i = 0; i < N; i++)

        {

            if (supervisorPref[index][i].equals(newPartner))

                return true;

            if (supervisorPref[index][i].equals(curPartner))

                return false;

        }

        return false;

    }

    /** get men index **/

    private int studentIndexOf(String str)

    {

        for (int i = 0; i < N; i++)

            if (student_list[i].equals(str))

                return i;

        return -1;

    }

    /** get women index **/

    private int supervisorIndexOf(String str)

    {

        for (int i = 0; i < N; i++)

            if (supervisor_list[i].equals(str))

                return i;

        return -1;

    }

    public void LoadStudentsInfo()
    {
      try
{

String sql = "SELECT * FROM student_tbl";
conn = DriverManager.getConnection("jdbc:mysql://localhost/ssmdb", "root","root"); 
pst= conn.prepareStatement(sql);
rs= pst.executeQuery();



i1.addItem("--Select--");
i2.addItem("--Select--");
i3.addItem("--Select--");
i4.addItem("--Select--");
i5.addItem("--Select--");
while(rs.next())
{
String fname6 = rs.getString("firstname");
String lname6 = rs.getString("lastname");

i1.addItem(fname6+" "+lname6 );
i2.addItem(fname6+" "+lname6 );
i3.addItem(fname6+" "+lname6 );
i4.addItem(fname6+" "+lname6 );
i5.addItem(fname6+" "+lname6 );


}

}
catch(Exception e)
{
JOptionPane.showMessageDialog(null,e);
}
    
    }
    

    /** print couples **/

    public void printMatchResult()

    {

        matchResult=("The Match between the students and supervisors is as follows : \n\n");

        for (int i = 0; i < N; i++)

        {

            matchResult+=(supervisorPartner[i] +" is the best fit for "+ supervisor_list[i]+"\n");
                try
{
        conn = DriverManager.getConnection("jdbc:mysql://localhost/ssmdb", "root","root");
        Statement st = (Statement) conn.createStatement();
        String insert = "INSERT INTO matchresult_tbl VALUES ('" +supervisorPartner[i]+ "', '" +supervisor_list[i]+ "')";
        st.executeUpdate(insert);

}
catch(Exception e)
{
        JOptionPane.showMessageDialog(null, e);
}
            

        }
        results.setText(matchResult+"\n This was matched using the Gale Shapley Algorithm");

    }
  
    
    public Project_Coordinator() {
        initComponents();
        setLocationRelativeTo(null);
    }
public void submit()
{

try
{
        String mFname = fname.getText();
        String mLname = lname.getText();
        String mEmail = email.getText();
        String mPhone = phone.getText();
        String in1 = i1.getSelectedItem().toString();
        String in2 = i2.getSelectedItem().toString();
        String in3 = i3.getSelectedItem().toString();
        String in4 = i4.getSelectedItem().toString();
        String in5 = i5.getSelectedItem().toString();
        
String alphabet = "ABCD0123456789";
        int i = 0;
        int Alphalaenge = alphabet.length();
        String total = "";
        while(i < 5){
        int rand = (int) (Math.random() * Alphalaenge);
        total += (alphabet.charAt(rand));
        ++i;
        }
        String genpwd = total;
        
        
        if(mFname.equals("") || mLname.equals("") || mEmail.equals("") || mPhone.equals("") || i1.getSelectedIndex()==0 || i2.getSelectedIndex()==0 || i3.getSelectedIndex()==0 || i4.getSelectedIndex()==0 || i5.getSelectedIndex()==0 )
        {
        JOptionPane.showMessageDialog(null,"Please fill in all feilds appropraitely");
        }
        else
        {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/ssmdb", "root","root");
        Statement st = (Statement) conn.createStatement();
        String insert = "INSERT INTO supervisor_tbl VALUES ('" +genpwd+ "','" +mFname+ "', '" +mLname+ "', '" +mEmail+ "', '" +mPhone+ "', '" +in1+ "', '" +in2+ "', '" +in3+ "', '" +in4+ "', '" +in5+ "')";
        st.executeUpdate(insert);
        JOptionPane.showMessageDialog(null,"Successfully registered Supervisor!");
        
        fname.setText("");
        lname.setText("");
        email.setText("");
        phone.setText("");
        i1.setSelectedIndex(0);
        i2.setSelectedIndex(0);
        i3.setSelectedIndex(0);
        i4.setSelectedIndex(0);
        i5.setSelectedIndex(0);
        }
        
}
catch(Exception e)
{
JOptionPane.showMessageDialog(null,  e);
}
}

public void SetValuesforMatch()
{
    
//setting names for student
    m[0] = std_list.getSelectedItem().toString();
    m[1] = std_list1.getSelectedItem().toString();
    m[2] = std_list2.getSelectedItem().toString();
    m[3] = std_list3.getSelectedItem().toString();
    m[4] = std_list4.getSelectedItem().toString();
    
//setting names for supervisor
    w[0] = sup_list1.getSelectedItem().toString();
    w[1] = sup_list2.getSelectedItem().toString();
    w[2] = sup_list3.getSelectedItem().toString();
    w[3] = sup_list4.getSelectedItem().toString();
    w[4] = sup_list5.getSelectedItem().toString();
    
//setting preferences for student
    
    for(int j= 0;j<=4;j++)
    {
    mp[0][j] = prefListStudent.getItemAt(j).toString();
    }
    
    for(int j= 0;j<=4;j++)
    {
    mp[1][j] = prefListStudent1.getItemAt(j).toString();
    }
    
    for(int j= 0;j<=4;j++)
    {
    mp[2][j] = prefListStudent2.getItemAt(j).toString();
    }
    
    for(int j= 0;j<=4;j++)
    {
    mp[3][j] = prefListStudent3.getItemAt(j).toString();
    }
    
    for(int j= 0;j<=4;j++)
    {
    mp[4][j] = prefListStudent4.getItemAt(j).toString();
    }
    
    //setting preferences for supervisors
    
    for(int j= 0;j<=4;j++)
    {
    wp[0][j] = prefListSup1.getItemAt(j).toString();
    }
    
    for(int j= 0;j<=4;j++)
    {
    wp[1][j] = prefListSup2.getItemAt(j).toString();
    }
    
    for(int j= 0;j<=4;j++)
    {
    wp[2][j] = prefListSup3.getItemAt(j).toString();
    }
    
    for(int j= 0;j<=4;j++)
    {
    wp[3][j] = prefListSup4.getItemAt(j).toString();
    }
    
    for(int j= 0;j<=4;j++)
    {
    wp[4][j] = prefListSup5.getItemAt(j).toString();
    }

}
public void loadstudent_tbl()
{
try
{
conn = DriverManager.getConnection("jdbc:mysql://localhost/ssmdb", "root","root");   
String sql = "SELECT student_ID,student_name,interest1,interest2,interest3,interest4,interest5 FROM matching_tbl ";
pst= conn.prepareStatement(sql);
rs= pst.executeQuery();
student_tbl.setModel(DbUtils.resultSetToTableModel(rs));
}
catch(Exception e)
{
JOptionPane.showMessageDialog(null,e);
}

}

public void loadSupervisor_tbl()
{
try
{
conn = DriverManager.getConnection("jdbc:mysql://localhost/ssmdb", "root","root");   
String sql = "SELECT supervisor_id,firstname,lastname,interest1,interest2,interest3,interest4,interest5 FROM supervisor_tbl ";
pst= conn.prepareStatement(sql);
rs= pst.executeQuery();
supervisor_tbl.setModel(DbUtils.resultSetToTableModel(rs));
}
catch(Exception e)
{
JOptionPane.showMessageDialog(null,e);
}
}
public void loadform()
{
LoadStudentsInfo();
loadstudent_tbl();
loadSupervisor_tbl();
}

public void performMatch()
{
SetValuesforMatch();
Project_Coordinator(m, w, mp, wp); 
JOptionPane.showMessageDialog(null,"Match Operation was successful. Go to match result tab for Results");
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        i1 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        i2 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        i3 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        i4 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        i5 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        fname = new javax.swing.JTextField();
        lname = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        phone = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        student_tbl = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        supervisor_tbl = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        std_list = new javax.swing.JComboBox();
        std_list1 = new javax.swing.JComboBox();
        std_list2 = new javax.swing.JComboBox();
        std_list3 = new javax.swing.JComboBox();
        std_list4 = new javax.swing.JComboBox();
        prefListStudent4 = new javax.swing.JComboBox();
        prefListStudent3 = new javax.swing.JComboBox();
        prefListStudent2 = new javax.swing.JComboBox();
        prefListStudent1 = new javax.swing.JComboBox();
        prefListStudent = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        sup_list5 = new javax.swing.JComboBox();
        sup_list4 = new javax.swing.JComboBox();
        sup_list3 = new javax.swing.JComboBox();
        sup_list2 = new javax.swing.JComboBox();
        sup_list1 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        prefListSup1 = new javax.swing.JComboBox();
        prefListSup2 = new javax.swing.JComboBox();
        prefListSup3 = new javax.swing.JComboBox();
        prefListSup4 = new javax.swing.JComboBox();
        prefListSup5 = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        results = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Project Coodinator Portal");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 0, 102));
        jPanel1.setToolTipText("");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel1.setText("First Name:");

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel2.setText("Last Name:");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel3.setText("Email:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel4.setText("Phone:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel5.setText("Student One:");

        i1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        i1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel6.setText("Student Two:");

        i2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        i2.setEnabled(false);
        i2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel7.setText("Student Three:");

        i3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        i3.setEnabled(false);
        i3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel8.setText("Student Four:");

        i4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        i4.setEnabled(false);
        i4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i4ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel9.setText("Student Five:");

        i5.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        i5.setEnabled(false);
        i5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i5ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fname.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        lname.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        email.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        phone.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(i4, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(i2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(i3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(i1, 0, 249, Short.MAX_VALUE)
                            .addComponent(fname)
                            .addComponent(lname)
                            .addComponent(email)
                            .addComponent(phone)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(i5, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(762, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(i1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(i2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(i3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(i4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(i5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Register Supervisor", jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        student_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        student_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                student_tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(student_tbl);

        supervisor_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        supervisor_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                supervisor_tblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(supervisor_tbl);

        jLabel11.setText("Student Requests with preferences");

        jLabel12.setText("List of Supervisors with Preferences");

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Selected Matching List of Students");

        std_list4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                std_list4ActionPerformed(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("List of Preferences of Student");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(std_list, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(std_list4, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(std_list3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(std_list2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(std_list1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(prefListStudent4, 0, 220, Short.MAX_VALUE)
                            .addComponent(prefListStudent3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(prefListStudent2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(prefListStudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(prefListStudent1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(std_list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prefListStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prefListStudent1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(std_list1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prefListStudent2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(std_list2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prefListStudent3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(std_list3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prefListStudent4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(std_list4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        sup_list5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sup_list5ActionPerformed(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Selected Matching List of Supervisors");

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("List of Preferences of Supervisors");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(sup_list1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sup_list5, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sup_list4, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sup_list3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sup_list2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(prefListSup5, 0, 220, Short.MAX_VALUE)
                            .addComponent(prefListSup4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(prefListSup3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(prefListSup1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(prefListSup2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sup_list1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prefListSup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prefListSup2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sup_list2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prefListSup3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sup_list3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prefListSup4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sup_list4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prefListSup5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sup_list5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jButton2.setText("Perform Match");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Student - Supervisor Matching", jPanel4);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        results.setEditable(false);
        results.setColumns(20);
        results.setLineWrap(true);
        results.setRows(5);
        jScrollPane3.setViewportView(results);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(412, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(285, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Match Results", jPanel8);

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Administrative Portal");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        submit();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void student_tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_student_tblMouseClicked
        // TODO add your handling code here:
           try
        {
        int row = student_tbl.getSelectedRow();
        
        if(row ==0 && r1==0)
        {
        r1++;
        String getstduent_ID=(student_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(student_tbl.getModel().getValueAt(row, 1).toString());        
        String interest1=(student_tbl.getModel().getValueAt(row, 2).toString());
        String interest2=(student_tbl.getModel().getValueAt(row, 3).toString());
        String interest3=(student_tbl.getModel().getValueAt(row, 4).toString());
        String interest4=(student_tbl.getModel().getValueAt(row, 5).toString());
        String interest5=(student_tbl.getModel().getValueAt(row, 6).toString());
        std_list.addItem(getfullname);
        prefListStudent.addItem(interest1);
        prefListStudent.addItem(interest2);
        prefListStudent.addItem(interest3);
        prefListStudent.addItem(interest4);
        prefListStudent.addItem(interest5);

        }
        if(row ==1 && r2==0)
        {
        r2++;
        String getstduent_ID=(student_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(student_tbl.getModel().getValueAt(row, 1).toString());        
        String interest1=(student_tbl.getModel().getValueAt(row, 2).toString());
        String interest2=(student_tbl.getModel().getValueAt(row, 3).toString());
        String interest3=(student_tbl.getModel().getValueAt(row, 4).toString());
        String interest4=(student_tbl.getModel().getValueAt(row, 5).toString());
        String interest5=(student_tbl.getModel().getValueAt(row, 6).toString());
        std_list1.addItem(getfullname);
        prefListStudent1.addItem(interest1);
        prefListStudent1.addItem(interest2);
        prefListStudent1.addItem(interest3);
        prefListStudent1.addItem(interest4);
        prefListStudent1.addItem(interest5);

        }
        if(row ==2 && r3==0)
        {
        r3++;
        String getstduent_ID=(student_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(student_tbl.getModel().getValueAt(row, 1).toString());        
        String interest1=(student_tbl.getModel().getValueAt(row, 2).toString());
        String interest2=(student_tbl.getModel().getValueAt(row, 3).toString());
        String interest3=(student_tbl.getModel().getValueAt(row, 4).toString());
        String interest4=(student_tbl.getModel().getValueAt(row, 5).toString());
        String interest5=(student_tbl.getModel().getValueAt(row, 6).toString());
        std_list2.addItem(getfullname);
        prefListStudent2.addItem(interest1);
        prefListStudent2.addItem(interest2);
        prefListStudent2.addItem(interest3);
        prefListStudent2.addItem(interest4);
        prefListStudent2.addItem(interest5);

        }
        if(row ==3 && r4==0)
        {
        r4++;
        String getstduent_ID=(student_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(student_tbl.getModel().getValueAt(row, 1).toString());        
        String interest1=(student_tbl.getModel().getValueAt(row, 2).toString());
        String interest2=(student_tbl.getModel().getValueAt(row, 3).toString());
        String interest3=(student_tbl.getModel().getValueAt(row, 4).toString());
        String interest4=(student_tbl.getModel().getValueAt(row, 5).toString());
        String interest5=(student_tbl.getModel().getValueAt(row, 6).toString());
        std_list3.addItem(getfullname);
        prefListStudent3.addItem(interest1);
        prefListStudent3.addItem(interest2);
        prefListStudent3.addItem(interest3);
        prefListStudent3.addItem(interest4);
        prefListStudent3.addItem(interest5);

        }
        if(row ==4 && r5==0)
        {
        r5++;
        String getstduent_ID=(student_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(student_tbl.getModel().getValueAt(row, 1).toString());        
        String interest1=(student_tbl.getModel().getValueAt(row, 2).toString());
        String interest2=(student_tbl.getModel().getValueAt(row, 3).toString());
        String interest3=(student_tbl.getModel().getValueAt(row, 4).toString());
        String interest4=(student_tbl.getModel().getValueAt(row, 5).toString());
        String interest5=(student_tbl.getModel().getValueAt(row, 6).toString());
        std_list4.addItem(getfullname);
        prefListStudent4.addItem(interest1);
        prefListStudent4.addItem(interest2);
        prefListStudent4.addItem(interest3);
        prefListStudent4.addItem(interest4);
        prefListStudent4.addItem(interest5);

        }
        

}
        catch(Exception e)
        {
        JOptionPane.showMessageDialog(null, e);
        }  
    }//GEN-LAST:event_student_tblMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        loadform();
    }//GEN-LAST:event_formWindowOpened

    private void std_list4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_std_list4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_std_list4ActionPerformed

    private void sup_list5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sup_list5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sup_list5ActionPerformed

    private void supervisor_tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supervisor_tblMouseClicked
        // TODO add your handling code here:
           try
        {
        int row = supervisor_tbl.getSelectedRow();
        
        if(row ==0 && s1==0)
        {
        s1++;
        String getsupervisor_ID=(supervisor_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(supervisor_tbl.getModel().getValueAt(row, 1).toString()+" "+supervisor_tbl.getModel().getValueAt(row, 2).toString());        
        String interest1=(supervisor_tbl.getModel().getValueAt(row, 3).toString());
        String interest2=(supervisor_tbl.getModel().getValueAt(row, 4).toString());
        String interest3=(supervisor_tbl.getModel().getValueAt(row, 5).toString());
        String interest4=(supervisor_tbl.getModel().getValueAt(row, 6).toString());
        String interest5=(supervisor_tbl.getModel().getValueAt(row, 7).toString());
        sup_list1.addItem(getfullname);
        prefListSup1.addItem(interest1);
        prefListSup1.addItem(interest2);
        prefListSup1.addItem(interest3);
        prefListSup1.addItem(interest4);
        prefListSup1.addItem(interest5);

        }
        if(row ==1 && s2==0)
        {
        s2++;
        String getsupervisor_ID=(supervisor_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(supervisor_tbl.getModel().getValueAt(row, 1).toString()+" "+supervisor_tbl.getModel().getValueAt(row, 2).toString());         
        String interest1=(supervisor_tbl.getModel().getValueAt(row, 3).toString());
        String interest2=(supervisor_tbl.getModel().getValueAt(row, 4).toString());
        String interest3=(supervisor_tbl.getModel().getValueAt(row, 5).toString());
        String interest4=(supervisor_tbl.getModel().getValueAt(row, 6).toString());
        String interest5=(supervisor_tbl.getModel().getValueAt(row, 7).toString());
        sup_list2.addItem(getfullname);
        prefListSup2.addItem(interest1);
        prefListSup2.addItem(interest2);
        prefListSup2.addItem(interest3);
        prefListSup2.addItem(interest4);
        prefListSup2.addItem(interest5);

        }
        if(row ==2 && s3==0)
        {
        s3++;
        String getsupervisor_ID=(supervisor_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(supervisor_tbl.getModel().getValueAt(row, 1).toString()+" "+supervisor_tbl.getModel().getValueAt(row, 2).toString());         
        String interest1=(supervisor_tbl.getModel().getValueAt(row, 3).toString());
        String interest2=(supervisor_tbl.getModel().getValueAt(row, 4).toString());
        String interest3=(supervisor_tbl.getModel().getValueAt(row, 5).toString());
        String interest4=(supervisor_tbl.getModel().getValueAt(row, 6).toString());
        String interest5=(supervisor_tbl.getModel().getValueAt(row, 7).toString());
        sup_list3.addItem(getfullname);
        prefListSup3.addItem(interest1);
        prefListSup3.addItem(interest2);
        prefListSup3.addItem(interest3);
        prefListSup3.addItem(interest4);
        prefListSup3.addItem(interest5);

        }
        if(row ==3 && s4==0)
        {
        s4++;
        String getsupervisor_ID=(supervisor_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(supervisor_tbl.getModel().getValueAt(row, 1).toString()+" "+supervisor_tbl.getModel().getValueAt(row, 2).toString());         
        String interest1=(supervisor_tbl.getModel().getValueAt(row, 3).toString());
        String interest2=(supervisor_tbl.getModel().getValueAt(row, 4).toString());
        String interest3=(supervisor_tbl.getModel().getValueAt(row, 5).toString());
        String interest4=(supervisor_tbl.getModel().getValueAt(row, 6).toString());
        String interest5=(supervisor_tbl.getModel().getValueAt(row, 7).toString());
        sup_list4.addItem(getfullname);
        prefListSup4.addItem(interest1);
        prefListSup4.addItem(interest2);
        prefListSup4.addItem(interest3);
        prefListSup4.addItem(interest4);
        prefListSup4.addItem(interest5);

        }
        if(row ==4 && s5==0)
        {
        s5++;
        String getsupervisor_ID=(supervisor_tbl.getModel().getValueAt(row, 0).toString());
        String getfullname=(supervisor_tbl.getModel().getValueAt(row, 1).toString()+" "+supervisor_tbl.getModel().getValueAt(row, 2).toString());          
        String interest1=(supervisor_tbl.getModel().getValueAt(row, 3).toString());
        String interest2=(supervisor_tbl.getModel().getValueAt(row, 4).toString());
        String interest3=(supervisor_tbl.getModel().getValueAt(row, 5).toString());
        String interest4=(supervisor_tbl.getModel().getValueAt(row, 6).toString());
        String interest5=(supervisor_tbl.getModel().getValueAt(row, 7).toString());
        sup_list5.addItem(getfullname);
        prefListSup5.addItem(interest1);
        prefListSup5.addItem(interest2);
        prefListSup5.addItem(interest3);
        prefListSup5.addItem(interest4);
        prefListSup5.addItem(interest5);

        }
        

}
        catch(Exception e)
        {
        JOptionPane.showMessageDialog(null, e);
        }  
    }//GEN-LAST:event_supervisor_tblMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        performMatch();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void i1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i1ActionPerformed
        // TODO add your handling code here:
         String temp = i1.getSelectedItem().toString();
        if(temp.equals("--Select--"))
        {

         }
        
        else
        {
        i1.removeAllItems();
        i1.addItem(temp);
        i2.setEnabled(true);
        i2.removeItem(i1.getSelectedItem().toString());
        }
       
    }//GEN-LAST:event_i1ActionPerformed

    private void i2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i2ActionPerformed
        // TODO add your handling code here:
            String temp = i2.getSelectedItem().toString();
        if(temp.equals("--Select--"))
        {
        
         }
        
        else
        {
        i2.removeAllItems();
        i2.addItem(temp);
        i3.setEnabled(true);
        i3.removeItem(i1.getSelectedItem().toString());
        i3.removeItem(i2.getSelectedItem().toString());
    }
    }//GEN-LAST:event_i2ActionPerformed

    private void i3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i3ActionPerformed
        // TODO add your handling code here:
          String temp = i3.getSelectedItem().toString();
        if(temp.equals("--Select--"))
        {
        //JOptionPane.showMessageDialog(null,"Please select a valid interest");
         }
        
        else
        {
        i3.removeAllItems();
        i3.addItem(temp);
        i4.setEnabled(true);
        i4.removeItem(i1.getSelectedItem().toString());
        i4.removeItem(i2.getSelectedItem().toString());
        i4.removeItem(i3.getSelectedItem().toString());
    }
    }//GEN-LAST:event_i3ActionPerformed

    private void i4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i4ActionPerformed
        // TODO add your handling code here:
           String temp = i4.getSelectedItem().toString();
        if(temp.equals("--Select--"))
        {
        //JOptionPane.showMessageDialog(null,"Please select a valid interest");
         }
        
        else
        {
        i4.removeAllItems();
        i4.addItem(temp);
        i5.setEnabled(true);
        i5.removeItem(i1.getSelectedItem().toString());
        i5.removeItem(i2.getSelectedItem().toString());
        i5.removeItem(i3.getSelectedItem().toString());
        i5.removeItem(i4.getSelectedItem().toString());
        i5.removeItemAt(0);
    }
    }//GEN-LAST:event_i4ActionPerformed

    private void i5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i5ActionPerformed
        // TODO add your handling code here:
           String temp = i5.getSelectedItem().toString();
        if(temp.equals("--Select--"))
        {
        //JOptionPane.showMessageDialog(null,"Please select a valid interest");
         }
    }//GEN-LAST:event_i5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Project_Coordinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Project_Coordinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Project_Coordinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Project_Coordinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Project_Coordinator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField email;
    private javax.swing.JTextField fname;
    private javax.swing.JComboBox i1;
    private javax.swing.JComboBox i2;
    private javax.swing.JComboBox i3;
    private javax.swing.JComboBox i4;
    private javax.swing.JComboBox i5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField lname;
    private javax.swing.JTextField phone;
    private javax.swing.JComboBox prefListStudent;
    private javax.swing.JComboBox prefListStudent1;
    private javax.swing.JComboBox prefListStudent2;
    private javax.swing.JComboBox prefListStudent3;
    private javax.swing.JComboBox prefListStudent4;
    private javax.swing.JComboBox prefListSup1;
    private javax.swing.JComboBox prefListSup2;
    private javax.swing.JComboBox prefListSup3;
    private javax.swing.JComboBox prefListSup4;
    private javax.swing.JComboBox prefListSup5;
    private javax.swing.JTextArea results;
    private javax.swing.JComboBox std_list;
    private javax.swing.JComboBox std_list1;
    private javax.swing.JComboBox std_list2;
    private javax.swing.JComboBox std_list3;
    private javax.swing.JComboBox std_list4;
    private javax.swing.JTable student_tbl;
    private javax.swing.JComboBox sup_list1;
    private javax.swing.JComboBox sup_list2;
    private javax.swing.JComboBox sup_list3;
    private javax.swing.JComboBox sup_list4;
    private javax.swing.JComboBox sup_list5;
    private javax.swing.JTable supervisor_tbl;
    // End of variables declaration//GEN-END:variables
}
