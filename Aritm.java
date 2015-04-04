/////////////////////////////////////////////////////////////////////
//
// Aritm
//
/**
 *    Aritm w/o sun.audio by M.O.B. as Java SE applet and application. 
 *    Copyright (C) 1992-2012 by Mikael O. Bonnier, Lund, Sweden.
 *    License: GNU GPL v3 or later, http://www.gnu.org/licenses/gpl.txt
 *    Donations are welcome to PayPal mikaelb@df.lth.se.
 *    The source code is at http://www.df.lth.se/~mikaelb/aritm/
 *
 *    @version        1.0.5
 *    @author         Mikael O. Bonnier
 */
//
// It was developed using JDK-1.0.2 on Windows 95 but also 
// tested on Slackware Linux from '96.
//
// Revision history:
// 1992:     MS-DOS version in C.
// 1997-Feb: 1.0bX  Beta versions as Java SE applet and application.
// 2012-Jan: 1.0.4  "private protected" replaced with  "private /* protected */".
//                   This change makes it work in OpenJDK/IcedTea.
// 2012-Jan: 1.0.5  Fixed an NFE-bug since key-events are handled buggy in OpenJDK.
//
// Suggestions, improvements, and bug-reports
// are always welcome to:
//                  Mikael Bonnier
//                  Osten Undens gata 88
//                  SE-227 62  LUND
//                  SWEDEN
//
// Or use my internet addresses:
//                  mikaelb@df.lth.se
//                  http://www.df.lth.se/~mikaelb/
//              _____
//             /   / \
// ***********/   /   \***********
//           /   /     \
// *********/   /       \*********
//         /   /   / \   \
// *******/   /   /   \   \*******
//       /   /   / \   \   \
// *****/   /   /***\   \   \*****
//     /   /__ /_____\   \   \
// ***/               \   \   \***
//   /_________________\   \   \
// **\                      \  /**
//    \______________________\/
//
// Mikael Bonnier
/////////////////////////////////////////////////////////////////////

import java.awt.*;
import java.io.*;
import java.net.*;
import java.applet.Applet;
import java.util.Random;
// import sun.audio.*;

public class Aritm extends Applet
{
   public int Copyright__C__1997_Mikael_Bonnier__Lund__Sweden;
   private /* protected */ Checkbox chkAdd;
   private /* protected */ Checkbox chkAddLev1;
   private /* protected */ Checkbox chkAddLev2;
   private /* protected */ Checkbox chkSub;
   private /* protected */ Checkbox chkSubLev1;
   private /* protected */ Checkbox chkSubLev2;
   private /* protected */ Checkbox chkSubFrom;
   private /* protected */ Checkbox chkMul;
   private /* protected */ Checkbox chkDiv;
   private /* protected */ CheckboxGroup grpNumSys;
   private /* protected */ Checkbox chkArabic;
   private /* protected */ Checkbox chkWords;
   private /* protected */ Checkbox chkRoman;
   private /* protected */ Button btnStart;
   private /* protected */ Label lblProblem;
   private /* protected */ TextField txtEntry;
   private /* protected */ Label lblResult;
   private /* protected */ long _lStart;
   protected String _sLang = "en";
   private /* protected */ String _sGood;
   private /* protected */ String _sProbPerMin;
   private /* protected */ String _sNotFin;
   private /* protected */ String _sProblems;
   private /* protected */ String _sAdd;
   private /* protected */ String _sSub;
   private /* protected */ String _sFrom;
   private /* protected */ String _sMul;
   private /* protected */ String _sDiv;
   private /* protected */ String _sArabic;
   private /* protected */ String _sWords;
   private /* protected */ String _sRoman;
   private /* protected */ String _sStart;
   private /* protected */ String _sStop;
   private /* protected */ String _sTryAgain;
   private /* protected */ String _sRight;
   private /* protected */ String _sProbsLeft;
   private /* protected */ String _sWrong;
   private /* protected */ String _sIs;
   private /* protected */ String _sIntPart;
   private /* protected */ String _sDividedBy;
   private /* protected */ String _sSFrom;
   private /* protected */ String _sPlus;
   private /* protected */ String _sMinus;
   private /* protected */ String _sTimes;
   private /* protected */ String _sFromSuffix;
   private /* protected */ char MULSIGN;
   private /* protected */ char DIVSIGN;
   private /* protected */ final int MAXPROB = 590;
   private /* protected */ Problem _prob[] = new Problem[MAXPROB];
   private /* protected */ int _index[] = new int[MAXPROB];
   private /* protected */ int _nUsedIndices;
   private /* protected */ int _iCurrent;
   private /* protected */ int _iIndex;
   private /* protected */ boolean _bRunning = false;
   private /* protected */ String _sProblem;   
   private /* protected */ Random rand = new Random();
   private /* protected */ int _total;  // Total number of Problems asked so far.
   private /* protected */ final int REQD = 1; // Number of times a Problem must be answered correctly
                                         // before it is removed from active part of _prob[].

   // private /* protected */ byte _audioSample[] = new byte[120];
   // private /* protected */ ContinuousAudioDataStream _audioStream;
   // private /* protected */ final int ATTNFQ = 1000;
   // private /* protected */ final int KEYBFQ = 3500;

   private /* protected */ boolean _bSite = false;
   protected int _nBGColor = 0xC0C0C0, _nText = 0x000000;
   protected boolean _bApp = false;

   public String getAppletInfo() 
   {
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      System.gc();

      return "Aritm v1.0.5\n" +
             "Copyright © 1992-1997 Mikael Bonnier, Lund, Sweden\n" +
             "All rights reserved\n" +
             "Internet e-mail: mikaelb@df.lth.se\n" +
             "WWW home page: http://www.df.lth.se/~mikaelb/\n\n" +
             "This product is licenced to:\n" +
             "Mikael Bonnier\n\n" + // <---
             "Operating system architecture: " + System.getProperty("os.arch") + "\n" +
             "Operating system name: " + System.getProperty("os.name") + "\n" +
             "Java vendor: " + System.getProperty("java.vendor") + "\n" +
             "Java version number: " + System.getProperty("java.version") + "\n" +
             "Free memory: " + Runtime.getRuntime().freeMemory()/1024 + " KB\n" +
             "Screen size: " + dim.width + "×" + dim.height + "\n" +
             "Directory: " + System.getProperty("user.dir") + "\n\n" +
             "DISCLAIMER\n" +
             "THIS PROGRAM IS USED AT YOUR OWN RISK.";
   }

   public String[][] getParameterInfo()
   {
      String pinfo[][] = {
         {"bgcolor", "#RRGGBB", "Background color"},
         {"text", "#RRGGBB", "Text color"},
         {"lang", "en|sv|fi", "Language"}
      };
      return pinfo;
   }

   public void paint(Graphics g)
   {
      if(_bSite)
         g.drawString("Aritm: Contact Mikael Bonnier, mikaelb@df.lth.se,"
            + " http://www.df.lth.se/~mikaelb/ for licensing info.", 0, 40);
   }

   public void init() 
   {
      setFont(new Font("Dialog", Font.PLAIN, 12));

      /*
      if(!_bApp && !getDocumentBase().toString().substring(0, 30).equals(  // <---
         "http://www.df.lth.se/~mikaelb/")) // <---
      {
         _bSite = true;
         return;
      }
      */

      setLayout(null);

      if(!_bApp)
      try
      {
         _nBGColor = Integer.parseInt(getParameter("bgcolor").substring(1), 16); // Skip #
         _nText = Integer.parseInt(getParameter("text").substring(1), 16); // Skip #
         _sLang = getParameter("lang").toLowerCase();
      }
      catch(RuntimeException e)
      {
      }
      setBackground(new Color(_nBGColor));
      setForeground(new Color(_nText));
      if(_sLang.equals("sv"))
      {
         _sGood = "Bra!!!   Du klarade ";
         _sProbPerMin = " uppgifter/minut.";
         _sNotFin = "Ej avslutat.   Du klarade ";
         _sProblems = " uppgifter.";
         _sAdd = "Addition";
         _sSub = "Subtraktion";
         _sFrom = "Från";
         _sMul = "Multiplikation";
         _sDiv = "Division";
         _sArabic = "Arabiska";
         _sWords = "Ord";
         _sRoman = "Romerska";
         _sStart = "Starta";
         _sStop = "Stoppa";
         _sTryAgain = "Försök igen.";
         _sRight = "Rätt! ";
         _sProbsLeft = " uppgifter kvar.";
         _sWrong = "Fel. ";
         _sIs = " är ";
         _sIntPart = "Heltalsdelen av ";
         _sDividedBy = " delat med ";
         _sSFrom = " från ";
         _sPlus = "plus";
         _sMinus = "minus";
         _sTimes = "gånger";
         _sFromSuffix = "";
         MULSIGN = '·';
         DIVSIGN = '/';
      }
      else if(_sLang.equals("fi"))
      {
         _sGood = "Hyvä!!!   Ratkaisit ";
         _sProbPerMin = " tehtävää/minuutissa.";
         _sNotFin = "Ei vielä loppu.   Ratkaisit ";
         _sProblems = " tehtävää.";
         _sAdd = "Yhteenlasku";
         _sSub = "Vähennyslasku";
         _sFrom = "Pois";
         _sMul = "Kertolasku";
         _sDiv = "Jakolasku";
         _sArabic = "Arabialaiset";
         _sWords = "Kirjoitetut";
         _sRoman = "Roomalaiset";
         _sStart = "Aloita";
         _sStop = "Seis";
         _sTryAgain = "Yritä uudelleen.";
         _sRight = "Oikein! ";
         _sProbsLeft = " tehtävää jäljellä.";
         _sWrong = "Väärin. ";
         _sIs = " on ";
         _sIntPart = "Kokonaislukuosa "; // Kokonaislukuosa X jaettuna Y:llä
         _sDividedBy = " jaettuna ";
         _sSFrom = " pois ";
         _sPlus = "plus";
         _sMinus = "miinus";
         _sTimes = "kertaa";
         _sFromSuffix = ":stä";
         MULSIGN = '·';
         DIVSIGN = '/';
      }
      else // enu
      {
         _sGood = "Good!!!   You made ";
         _sProbPerMin = " problems/minute.";
         _sNotFin = "Not finished.   You made ";
         _sProblems = " problems.";
         _sAdd = "Addition";
         _sSub = "Subtraction";
         _sFrom = "From";
         _sMul = "Multiplication";
         _sDiv = "Division";
         _sArabic = "Arabic";
         _sWords = "Words";
         _sRoman = "Roman";
         _sStart = "Start";
         _sStop = "Stop";
         _sTryAgain = "Try again.";
         _sRight = "Right! ";
         _sProbsLeft = " problems left.";
         _sWrong = "Wrong. ";
         _sIs = " is ";
         _sIntPart = "The integer part of ";
         _sDividedBy = " divided by ";
         _sSFrom = " from ";
         _sPlus = "plus";
         _sMinus = "minus";
         _sTimes = "times";
         _sFromSuffix = "";
         MULSIGN = '×';
         DIVSIGN = '÷';
      }
      int nCW = 105;
      int nRH = 29;

      chkAdd = new Checkbox(_sAdd);
      chkAdd.setState(true);
      add(chkAdd);
      chkAdd.reshape(0, 0, nCW, nRH);

      chkAddLev1 = new Checkbox("1");
      chkAddLev1.setState(true);
      add(chkAddLev1);
      chkAddLev1.reshape(nCW, 0, nCW/2, nRH);

      chkAddLev2 = new Checkbox("2");
      add(chkAddLev2);
      chkAddLev2.reshape(3*nCW/2, 0, nCW/2, nRH);

      chkSub = new Checkbox(_sSub);
      add(chkSub);
      chkSub.reshape(0, nRH, nCW, nRH);

      chkSubLev1 = new Checkbox("1");
      chkSubLev1.disable();
      chkSubLev1.setState(true);
      add(chkSubLev1);
      chkSubLev1.reshape(nCW, nRH, nCW/2, nRH);

      chkSubLev2 = new Checkbox("2");
      chkSubLev2.disable();
      add(chkSubLev2);
      chkSubLev2.reshape(3*nCW/2, nRH, nCW/2, nRH);

      chkSubFrom = new Checkbox(_sFrom);
      chkSubFrom.disable();
      add(chkSubFrom);
      chkSubFrom.reshape(2*nCW, nRH, nCW/2, nRH);

      chkMul = new Checkbox(_sMul);
      add(chkMul);
      chkMul.reshape(0, 2*nRH, nCW, nRH);

      chkDiv = new Checkbox(_sDiv);
      add(chkDiv);
      chkDiv.reshape(0, 3*nRH, nCW, nRH);

      grpNumSys = new CheckboxGroup();

      chkArabic = new Checkbox(_sArabic, grpNumSys, true);
      add(chkArabic);
      chkArabic.reshape(4*nCW, 0, nCW, nRH);

      chkWords = new Checkbox(_sWords, grpNumSys, false);
      add(chkWords);
      chkWords.reshape(4*nCW, nRH, nCW, nRH);

      chkRoman = new Checkbox(_sRoman, grpNumSys, false);
      add(chkRoman);
      chkRoman.reshape(4*nCW, 2*nRH, nCW, nRH);

      lblProblem = new Label();
      lblProblem.disable();
      add(lblProblem);
      lblProblem.reshape(0, 4*nRH, 5*nCW, nRH);

      txtEntry = new TextField(80);
      txtEntry.disable();
      txtEntry.setBackground(Color.white);
      add(txtEntry);
      txtEntry.reshape(0, 5*nRH, nCW, nRH);

      lblResult = new Label();
      lblResult.setText(String.valueOf(countProblems()) + _sProblems);
      add(lblResult);
      lblResult.reshape(nCW, 5*nRH, 3*nCW, nRH);

      btnStart = new Button(_sStart);
      add(btnStart);
      btnStart.reshape(4*nCW, 5*nRH, nCW, nRH);

      validate();
      // _audioStream = new ContinuousAudioDataStream(new AudioData(_audioSample));
   }

   public boolean handleEvent(Event evt) 
   {
      switch(evt.id)
      {
         case Event.ACTION_EVENT:
            if(evt.target == btnStart)
            {
               if(!_bRunning && countProblems() > 0)
               {
                  btnStart.setLabel(_sStop);
                  generate();
                  _total = 0;
                  lblResult.setText(_nUsedIndices + _sProblems);
                  ask();
                  _lStart = System.currentTimeMillis();
                  lblProblem.enable();
                  txtEntry.enable();
                  txtEntry.requestFocus();
                  _bRunning = true;
                  genSelEnable(false);
               }
               else
               {
                  btnStart.setLabel(_sStart);
                  displayScore();
                  _bRunning = false;
                  genSelEnable(true);
               }
            }
            else if(evt.target == txtEntry)
            {
               if(_bRunning)
               {
                  if(!check())
                  {
                     btnStart.setLabel(_sStart);
                     displayScore();
                     _bRunning = false;
                     genSelEnable(true);
                  }
                  else
                     ask();
               }
            }
            else if(evt.target == chkArabic || evt.target == chkWords || evt.target == chkRoman)
            {
               if(_bRunning)
               {
                  _sProblem = expandProblem(_iCurrent);
                  lblProblem.setText(_sProblem);
               }
            }
            else if(evt.target == chkAdd || evt.target == chkSub)
            {
               genSelEnable(!_bRunning);
               lblResult.setText(String.valueOf(countProblems()) + _sProblems);
            }
            else if(evt.target == chkMul || evt.target == chkDiv
                 || evt.target == chkAddLev1 || evt.target == chkAddLev2
                 || evt.target == chkSubLev1 || evt.target == chkSubLev2)
               lblResult.setText(String.valueOf(countProblems()) + _sProblems);
            break;
         case Event.KEY_PRESS:
            if(evt.target == txtEntry)
            {
               lblResult.setText("");
               if(evt.key != '\n' && evt.key != '\b' && evt.key != 127)
                  if(evt.key < '0' || evt.key > '9')
                  {
                     // bell(KEYBFQ);
                     return true; // Doesn't stop events in OpenJDK.
                  }
            }
            break;
      }
      return super.handleEvent(evt);
   }

   void genSelEnable(boolean bCond)
   {
      chkAdd.enable(bCond);
      chkAddLev1.enable(bCond && chkAdd.getState());
      chkAddLev2.enable(bCond && chkAdd.getState());
      chkSub.enable(bCond);
      chkSubLev1.enable(bCond && chkSub.getState());
      chkSubLev2.enable(bCond && chkSub.getState());
      chkSubFrom.enable(bCond && chkSub.getState());
      chkMul.enable(bCond);
      chkDiv.enable(bCond);
   }

   void displayScore()
   {
      long lStop = System.currentTimeMillis();
      if(_nUsedIndices == 0)
      {
         lblResult.setText(_sGood + (_total*60000)/(lStop-_lStart) + _sProbPerMin);
         // bell(880); bell(1108.73); bell(1318.51); bell(1760);
      }
      else
         lblResult.setText(_sNotFin + (_total*60000)/(lStop-_lStart) + _sProbPerMin);
      lblProblem.disable();
      txtEntry.disable();
   }

   int countProblems()
   {
      int nProblems = 0;
      if(chkAdd.getState())
      {
         if(chkAddLev1.getState())
            nProblems += 100;
         if(chkAddLev2.getState())
            nProblems += 100;
      }
      if(chkSub.getState())
      {
         if(chkSubLev1.getState())
            nProblems += 100;
         if(chkSubLev2.getState())
            nProblems += 100;
      }
      if(chkMul.getState())
         nProblems += 100;
      if(chkDiv.getState())
         nProblems += 90;
      return nProblems;
   }

   void generate()
   {
      _nUsedIndices = _iIndex = 0;
      if(chkAdd.getState())
      {
         if(chkAddLev1.getState())
            genAdd(0);
         if(chkAddLev2.getState())
            genAdd(1);
      }
      if(chkSub.getState())
      {
         if(chkSubLev1.getState())
            if(!chkSubFrom.getState())
               genSub(0);
            else
               genSub(1);
         if(chkSubLev2.getState())
            if(!chkSubFrom.getState())
               genSub(2);
            else
               genSub(3);
      }
      if(chkMul.getState())
         genMul();
      if(chkDiv.getState())
         genDiv();
      for(int i = 0; i < _nUsedIndices; ++i)
         _index[i] = i;
      shuffle();
   }

   void genAdd(int mod)
   {
      int i, j;

      for(i=0; i<=9; ++i)
         for(j=0; j<=9; ++j) {
            _prob[_nUsedIndices] = new Problem();
            _prob[_nUsedIndices].op1 = (mod==1 ? 10*((rand.nextInt() & Integer.MAX_VALUE)%8+1) : 0) + i;
            _prob[_nUsedIndices].sign = '+';
            _prob[_nUsedIndices].op2 = j;
            _prob[_nUsedIndices].ans = _prob[_nUsedIndices].op1+j;
            _nUsedIndices++;
         }
   }

   void genSub(int mod)
   {
      int i, j;

      for(i=0; i<=9; ++i)
         for(j=i; j<=9+i; ++j) 
         {
            _prob[_nUsedIndices] = new Problem();
            _prob[_nUsedIndices].op1 = (mod==2 || mod==3 ? 10*((rand.nextInt() & Integer.MAX_VALUE)%9+1) : 0)+j;
            _prob[_nUsedIndices].sign  =  mod==1 || mod==3 ? 'f' : '-';
            _prob[_nUsedIndices].op2 = i;
            _prob[_nUsedIndices].ans = _prob[_nUsedIndices].op1-i;
            _nUsedIndices++;
         }
   }

   void genMul()
   {
      int i, j;

      for(i=0; i<=9; ++i)
         for(j=0; j<=9; ++j) 
         {
            _prob[_nUsedIndices] = new Problem();
            _prob[_nUsedIndices].op1 = i;
            _prob[_nUsedIndices].sign = MULSIGN;
            _prob[_nUsedIndices].op2 = j;
            _prob[_nUsedIndices].ans = i*j;
            _nUsedIndices++;
         }
   }

   void genDiv()
   {
      int q, d;

      for(q=0; q<=9; ++q)
         for(d=1; d<=9; ++d) 
         {
            _prob[_nUsedIndices] = new Problem();
            _prob[_nUsedIndices].op1 = q*d+(rand.nextInt() & Integer.MAX_VALUE)%d;
            _prob[_nUsedIndices].sign = DIVSIGN;
            _prob[_nUsedIndices].op2 = d;
            _prob[_nUsedIndices].ans = q;
            _nUsedIndices++;
         }
   }

   void ask()
   {
      _iCurrent = _index[_iIndex];
      _sProblem = expandProblem(_iCurrent);
      lblProblem.setText(_sProblem);
      txtEntry.setText("");
   }

   boolean check()
   {
      int nAns = -1;
      String sAns = txtEntry.getText();

      try
      {
         nAns = Integer.parseInt(sAns);
      } catch(Exception nfe) // Bug fix for OpenJDK since key events cannot be stopped.
      {
         txtEntry.setText("");
         sAns = "";
      }
      if(sAns.length() == 0)
      {
         lblResult.setText(_sTryAgain);
         // bell(ATTNFQ);
         return true;
      }
      ++_total;
      
      if(nAns == _prob[_iCurrent].ans)
      {
         ++_prob[_iCurrent].correct;
         int nLeft = 0;
         for(int i = 0; i < _nUsedIndices; ++i)
            nLeft = nLeft + REQD - _prob[_index[i]].correct;
         lblResult.setText(_sRight + nLeft + _sProbsLeft);
         ++_iIndex;
      }
      else 
      {
         --_prob[_iCurrent].correct;
         lblResult.setText(_sWrong + _sProblem.substring(0, _sProblem.length() - 1) + _sIs + _prob[_iCurrent].ans + ".");
         packArray();
         shuffle();      
         _iIndex = 0;
         // bell(ATTNFQ);
      }
      if(_iIndex >= _nUsedIndices)
      {
         _iIndex = 0;
         return packArray();
      }
      return true;
   }

   boolean packArray()
   {
      int i, j, n = 0;

      for(i = 0; i < _nUsedIndices; ++i) 
      {
         j = _index[i];
         if(_prob[j].correct < REQD)
            _index[n++] = j;
      }
      _nUsedIndices = n;
      return _nUsedIndices != 0;
   }

   void shuffle()
   {
      int i, j, temp;

      for(i = 0; i < _nUsedIndices; ++i) 
      {
         j = (rand.nextInt() & Integer.MAX_VALUE) % _nUsedIndices;
         temp = _index[i];
         _index[i] = _index[j];
         _index[j] = temp;
      }
   }

   String expandProblem(int indx)
   {
      StringBuffer sRet;
      boolean bLitr, bRom;
      String sOpertn = "";

      bLitr = chkWords.getState();
      bRom = chkRoman.getState();
      switch(_prob[indx].sign) 
      {
         case '/': 
         case '÷':
            if(bLitr) 
            {
               sRet = new StringBuffer(_sIntPart + intToWords(_prob[indx].op1, 0));
               sRet.append(_sDividedBy + intToWords(_prob[indx].op2, 2) + "?");
            }
            else if(bRom)
               sRet = new StringBuffer(_sIntPart + intToRoman(_prob[indx].op1) + _prob[indx].sign + intToRoman(_prob[indx].op2) + "=");
            else
               sRet = new StringBuffer(_sIntPart + _prob[indx].op1 + _prob[indx].sign + _prob[indx].op2 + "=");
            break;
         case 'f':
            if(bLitr) 
            {
               sRet = new StringBuffer(intToWords(_prob[indx].op2, 0));
               sRet.setCharAt(0, Character.toUpperCase(sRet.charAt(0)));
               sRet.append(_sSFrom + intToWords(_prob[indx].op1, 1) + "?");
            }
            else if(bRom)
               sRet = new StringBuffer(intToRoman(_prob[indx].op2) + _sSFrom + intToRoman(_prob[indx].op1) + _sFromSuffix + "=");
            else
               sRet = new StringBuffer(String.valueOf(_prob[indx].op2) + _sSFrom + _prob[indx].op1 + _sFromSuffix + "=");
            break;
         default:
            if(bLitr) 
            {
               switch(_prob[indx].sign) 
               {
                  case '+': sOpertn = _sPlus;
                     break;
                  case '-': sOpertn = _sMinus;
                     break;
                  case '·': case '×': sOpertn = _sTimes;
                     break;
               }
               sRet = new StringBuffer(intToWords(_prob[indx].op1, 0));
               sRet.setCharAt(0, Character.toUpperCase(sRet.charAt(0)));
               sRet.append(" " + sOpertn + " " + intToWords(_prob[indx].op2, 0) + "?");
            }
            else if(bRom)
               sRet = new StringBuffer(intToRoman(_prob[indx].op1) + _prob[indx].sign + intToRoman(_prob[indx].op2) + "=");
            else
               sRet = new StringBuffer(String.valueOf(_prob[indx].op1) + _prob[indx].sign + _prob[indx].op2 + "=");
      } // switch   
      return sRet.toString();
   }

   String intToWords(int number, int prep)  // number<1000
   {
      if(_sLang.equals("sv"))
         return intToWordsSv(number);
      if(_sLang.equals("fi"))
         return intToWordsFi(number, prep);
      else
         return intToWordsEn(number);
   }

   String intToWordsSv(int number)
   {
      String unitword[] = {"noll", "ett", "två", "tre", "fyra", "fem",
                              "sex", "sju", "åtta", "nio"};
      // Vardagliga ord.
      String teenword[] = {"tio", "elva", "tolv", "tretton", "fjorton",
                     "femton", "sexton", "sjutton", "arton", "nitton"};
      String tensword[] = {"noll", "tio", "tjugo", "tretti", "förti",
                          "femti", "sexti", "sjutti", "åtti", "nitti"};
      String literal = "";
      char sDec[];
      int n, lenleft;

      if(number>=1000)
         return literal;
      sDec = String.valueOf(number).toCharArray();
      n = 0;
      lenleft = sDec.length;
      if(lenleft == 3) 
      {
         literal += unitword[sDec[n] - '0'];
         literal += "hundra";
         ++n;
         lenleft = 2;
      }
      if(lenleft == 2) 
      {
         if(sDec[n] == '0') 
         {
            if(sDec[++n] == '0')
               lenleft = 0;
            else
               lenleft = 1;
         }
         else if(sDec[n] == '1') 
         {
            literal += teenword[sDec[n+1] - '0'];
            lenleft = 0;
         }
         else 
         {
            literal += tensword[sDec[n] - '0'];
            if(sDec[++n] == '0')
               lenleft = 0;
            else
               lenleft = 1;
         }
      }
      if(lenleft == 1)
         literal += unitword[sDec[n] - '0'];
      return literal;
   }

   String intToWordsFi(int number, int prep)
   {
      String[] unitword, teenword, tensword;
      String sHundred;
      switch(prep)
      {
         default: // No preposition
            String[] unitwordNon = {"nolla", "yksi", "kaksi", "kolme", "neljä", "viisi",
               "kuusi", "seitsemän", "kahdeksan", "yhdeksän"};
            String[] teenwordNon = {"kymmenen", "yksitoista", "kaksitoista", "kolmetoista", "neljätoista",
               "viisitoista", "kuusitoista", "seitsemäntoista", "kahdeksantoista", "yhdeksäntoista"};
            String[] tenswordNon = {"nolla", "kymmenen", "kaksikymmentä", "kolmekymmentä", "neljäkymmentä",
               "viisikymmentä", "kuusikymmentä", "seitsemänkymmentä", "kahdeksankymmentä", "yhdeksänkymmentä"};
            unitword = unitwordNon;
            teenword = teenwordNon;
            tensword = tenswordNon;
            sHundred = "sata";
            break;
         case 1: // From
            String[] unitwordFrom = {"nollasta", "yhdestä", "kahdesta", "kolmesta", "neljästä", "viidestä",
               "kuudesta", "seitsemästä", "kahdeksasta", "yhdeksästä"};
            String[] teenwordFrom = {"kymmenestä", "yhdestätoista", "kahdestatoista", "kolmestatoista", "neljästätoista",
               "viidestätoista", "kuudestatoista", "seitsemästätoista", "kahdeksastatoista", "yhdeksästätoista"};
            String[] tenswordFrom = {"nollasta", "kymmenestä", "kahdestakymmenestä", "kolmestaenestä", "neljästäkymmenestä",
               "viidestäkymmenestä", "kuudestakymmenestä", "seitsemästäkymmenestä", "kahdeksastakymmenestä", "yhdeksästäkymmenestä"};
            unitword = unitwordFrom;
            teenword = teenwordFrom;
            tensword = tenswordFrom;
            sHundred = "sadasta";
            break;
         case 2: // By
            String[] unitwordBy = {"nollalla", "yhdellä", "kahdella", "kolmella", "neljällä", "viidellä",
               "kuudella", "seitsemällä", "kahdeksalla", "yhdeksällä"};
            String[] teenwordBy = {"kymmenellä", "yhdellätoista", "kahdellatoista", "kolmellatoista", "neljällätoista",
               "viidelätoista", "kuudellatoista", "seitsemällätoista", "kahdeksallatoista", "yhdeksällätoista"};
            String[] tenswordBy = {"nollalla", "kymmenellä", "kahdellakymmenellä", "kolmellakymmenellä", "neljälläkymmenellä",
               "viidelläkymmenellä", "kuudellakymmenellä", "seitsemälläkymmenellä", "kahdeksallakymmenellä", "yhdeksälläkymmenellä"};
            unitword = unitwordBy;
            teenword = teenwordBy;
            tensword = tenswordBy;
            sHundred = "sadalla";
            break;
      }
      String literal = "";
      char sDec[];
      int n, lenleft;

      if(number>=1000)
         return literal;
      sDec = String.valueOf(number).toCharArray();
      n = 0;
      lenleft = sDec.length;
      if(lenleft == 3) 
      {
         if(sDec[n] != 1)
            literal += unitword[sDec[n] - '0'];
         literal += sHundred;
         ++n;
         lenleft = 2;
      }
      if(lenleft == 2) 
      {
         if(sDec[n] == '0') 
         {
            if(sDec[++n] == '0')
               lenleft = 0;
            else
               lenleft = 1;
         }
         else if(sDec[n] == '1') 
         {
            literal += teenword[sDec[n+1] - '0'];
            lenleft = 0;
         }
         else 
         {
            literal += tensword[sDec[n] - '0'];
            if(sDec[++n] == '0')
               lenleft = 0;
            else
               lenleft = 1;
         }
      }
      if(lenleft == 1)
         literal += unitword[sDec[n] - '0'];
      return literal;
   }

   String intToWordsEn(int number)
   {
      String unitword[] = {"zero", "one", "two", "three", "four", "five",
                              "six", "seven", "eight", "nine"};
      String teenword[] = {"ten", "eleven", "twelve", "thirteen", "fourteen",
                              "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
      String tensword[] = {"zero", "ten", "twenty", "thirty", "forty",
                              "fifty", "sixty", "seventy", "eighty", "ninety"};
      String literal = "";
      char sDec[];
      int n, lenleft;

      if(number>=1000)
         return literal;
      sDec = String.valueOf(number).toCharArray();
      n = 0;
      lenleft = sDec.length;
      if(lenleft == 3) 
      {
         literal += unitword[sDec[n] - '0'];
         literal += " hundred ";
         ++n;
         lenleft = 2;
      }
      if(lenleft == 2) 
      {
         if(sDec[n] == '0') 
         {
            if(sDec[++n] == '0')
               lenleft = 0;
            else
               lenleft = 1;
         }
         else if(sDec[n] == '1') 
         {
            literal += teenword[sDec[n+1] - '0'];
            lenleft = 0;
         }
         else 
         {
            literal += tensword[sDec[n] - '0'];
            if(sDec[++n] == '0')
               lenleft = 0;
            else
            {
               lenleft = 1;
               literal += "-";
            }
         }
      }
      if(lenleft == 1)
         literal += unitword[sDec[n] - '0'];
      return literal.trim();
   }

   String intToRoman(int nNumber)
   {
      return intToRoman(nNumber, false);
   }

   String intToRoman(int nNumber, boolean bLegal)
   {
      String sRet = "";
      int V = nNumber;
      int D;

      if(V == 0)
         return "0";
      if(bLegal)
      {
         for(int J = (int)Math.floor(Math.log(V+1.0E-99)/Math.log(10)); J >= 0; --J)
         {
            D = (int)(Math.floor(V/Math.pow(10,J))*Math.pow(10,J));
            sRet += romanSub(D);
            V = V-D;
         }
      }
      else
         sRet = romanSub(V);
      return sRet;
   }

   String romanSub(int N)
   {
      String sRet = "";
      int S, A, E = 0;
      while(N != 0)
      {
         S = romanCeil(N);
         A = S-N;
         if(A>0)
            E = romanCeil(A);
         if(A>0 && S>2*E)
         {
            sRet += romanPrint(E);
            sRet += romanPrint(S);
            N = N-(S-E);
         }
         else
         {
            S = romanFloor(N);
            sRet += romanPrint(S);
            N = N-S;
         }
      }
      return sRet;
   }

   int romanCeil(int M)
   {
      int F = (int)Math.floor(Math.log(M)/Math.log(10));
      if(M == Math.pow(10,F))
         return M;
      if(M > 5*Math.pow(10,F))
         return 10*(int)Math.pow(10,F);
      else
         return 5*(int)Math.pow(10,F);
   }

   int romanFloor(int M)
   {
      int F = (int)Math.floor(Math.log(M)/Math.log(10));
      if(M>=5*Math.pow(10,F))
         return 5*(int)Math.pow(10,F);
      else
         return (int)Math.pow(10,F);
   }

   String romanPrint(int M)
   {
      String sRet = "";
      while(M>1000)
      {
         sRet += "=";
         M /= 1000;
      }
      if(M==1)
         sRet += "I";
      else if(M==5)
         sRet += "V";
      else if(M==10)
         sRet += "X";
      else if(M==50)
         sRet += "L";
      else if(M==100)
         sRet += "C";
      else if(M==500)
         sRet += "D";
      else if(M==1000)
         sRet += "M";
      return sRet;
   }

/*
   void bell(double dFreq) 
   {
      prepTone(dFreq);
      AudioPlayer.player.start(_audioStream);
      try {
         Thread.sleep(100);
      } catch(InterruptedException e)
      {
      }
      AudioPlayer.player.stop(_audioStream);
   }

   void prepTone(double dFreq)
   {
      dFreq = Math.round(_audioSample.length/8192.0*dFreq)*8192.0/_audioSample.length;
      // System.out.println(dFreq);
      AudioPlayer.player.stop(_audioStream);
      for(int i = 0; i < _audioSample.length; ++i)
         _audioSample[i] = int2ulaw((int)(8191*Math.sin(i/8192.0*2.0*Math.PI*dFreq)));
   }

   static byte int2ulaw(int ch) // -8191 <= ch <= 8191
   {
      int mask;

      if(ch < 0) 
      {
         ch = -ch;
         mask = 0x7f;
      }
      else 
         mask = 0xff;

      if(ch < 32) 
         ch = 0xF0 | 15 - (ch/2);
      else if(ch < 96) 
         ch = 0xE0 | 15 - (ch-32)/4;
      else if(ch < 224) 
         ch = 0xD0 | 15 - (ch-96)/8;
      else if(ch < 480) 
         ch = 0xC0 | 15 - (ch-224)/16;
      else if(ch < 992 ) 
         ch = 0xB0 | 15 - (ch-480)/32;
      else if (ch < 2016) 
         ch = 0xA0 | 15 - (ch-992)/64;
      else if (ch < 4064) 
         ch = 0x90 | 15 - (ch-2016)/128;
      else if (ch < 8160) 
         ch = 0x80 | 15 - (ch-4064)/256;
      else 
         ch = 0x80;
      return (byte)(mask & ch);
   }
*/
}

class Problem
{
   protected int op1;
//   char op1exp;
   protected char sign;
   protected int op2;
//   char op2exp;
   protected int ans;
//   char ansexp;
   protected int correct;
}

class AritmApp extends Frame
{
   public int Copyright__C__1997_Mikael_Bonnier__Lund__Sweden;
   private /* protected */ Aritm pnlAritm;
   private /* protected */ MenuItem mnuExit;
   private /* protected */ MenuItem mnuHelp;
   private /* protected */ MenuItem mnuAbout;
   private /* protected */ Button btnAboutOK;

   public static void main(String args[]) 
   {
      AritmApp frm = new AritmApp("Aritm");
      frm.pnlAritm = new Aritm();
      frm.add("Center", frm.pnlAritm);
      frm.pack();
       
      int i = 0;
      String arg;
      while(i < args.length && args[i].startsWith("-"))
      {
         arg = args[i++];
         if(arg.equals("-bgcolor"))
            if(i < args.length)
               frm.pnlAritm._nBGColor = Integer.parseInt(args[i++].substring(1), 16); // Skip #
            else
               System.err.println("-bgcolor require a background color #RRGGBB");
         if(arg.equals("-text"))
            if(i < args.length)
               frm.pnlAritm._nText = Integer.parseInt(args[i++].substring(1), 16); // Skip #
            else
               System.err.println("-text require a text color #RRGGBB");
         if(arg.equals("-lang"))
            if(i < args.length)
               frm.pnlAritm._sLang = args[i++].toLowerCase();
            else
               System.err.println("-lang require a en|sv|fi argument");
      }
      frm.pnlAritm._bApp = true;
      frm.pnlAritm.init();
      frm.show();
      Insets ins = frm.insets();
      frm.resize(525 + ins.left + ins.right, 174 + ins.top + ins.top + ins.bottom);
   }

   AritmApp(String s)
   {
      super(s);
      MenuBar mnuBar = new MenuBar();
      Menu mnu = new Menu("File");
      mnuExit = new MenuItem("Exit");
      mnu.add(mnuExit);
      mnuBar.add(mnu);
      mnu = new Menu("Edit");
      mnu.disable();
      mnuBar.add(mnu);
      mnu = new Menu("Help");
      mnuBar.setHelpMenu(mnu);
      mnuHelp = new MenuItem("Help");
      mnu.add(mnuHelp);
      mnu.addSeparator();
      mnuAbout = new MenuItem("About Aritm");
      mnu.add(mnuAbout);
      setMenuBar(mnuBar);
   }

   public boolean handleEvent(Event evt) 
   {
      switch(evt.id)
      {
         case Event.WINDOW_DESTROY:
            if(evt.target == this)
            {
               System.exit(0);
               return true;
            }
            else
               ((Window)evt.target).dispose();
         case Event.ACTION_EVENT:
            if(evt.target == btnAboutOK)
               ((Window)btnAboutOK.getParent().getParent()).dispose();
            else if(evt.target.getClass().getName().equals("java.awt.MenuItem"))
            {
               if(evt.target == mnuExit)
               {
                  System.exit(0);
                  return true;
               }
               else if(evt.target == mnuHelp)
               {
                  try
                  {
                     Runtime.getRuntime().exec(System.getProperty("user.dir") + System.getProperty("file.separator") + "help.bat");
                  } catch(IOException ex) 
                  {
                     System.err.println("IOException: " + ex);
                  }
               }
               else if(evt.target == mnuAbout)
               {
                  Dialog dlg = new Dialog(this, "About Aritm", true);
                  dlg.setResizable(false);
                  Panel pnl = new Panel();
                  dlg.add("Center", pnl);
                  dlg.pack();
                  pnl.setBackground(pnlAritm.getBackground());
                  pnl.setForeground(pnlAritm.getForeground());
                  pnl.setLayout(null);
                  TextArea txt = new TextArea(pnlAritm.getAppletInfo());
                  txt.setEditable(false);
                  pnl.add(txt);
                  txt.reshape(0, 0, 300, 180);
                  btnAboutOK = new Button("OK");
                  pnl.add(btnAboutOK);
                  btnAboutOK.reshape(260, 181, 40, 20);
                  dlg.show();
                  Insets ins = dlg.insets();
                  dlg.resize(300 + ins.left + ins.right, 201 + ins.top + ins.bottom);
                  dlg.validate();
               }
            }
            break;
      }
      return super.handleEvent(evt);
   }
}
