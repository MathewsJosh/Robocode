package pacotao;
import robocode.*;
import java.awt.Color;
import java.awt.geom.*;
//import robocode.TeamRobot;
// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html
/** * Malaquias - a robot by (MATHEUS DOS REIS CASARIM, MATHEWS EDWIRDS GOMES ALMEIDA) */

public class Malaquias extends AdvancedRobot
{
    /*** run: Testinho's default behavior*/
    static Rectangle2D.Double campo, limite, limite2, limite3;
    static Point2D.Double posInimigo, marcarInimigo;
    double radar=1800, angIni=0, tiro, inimigoDist=0, anguloInimigo=0;
	int gira=90,direcao=1, x=0,y=0;


    public void run() {
		setBodyColor(Color.white);
		setGunColor(Color.blue);
		setRadarColor(Color.blue);
		setScanColor(Color.green);
		setBulletColor(Color.blue);
		
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        setTurnRadarRight(radar);

        campo=new Rectangle2D.Double(100, 100, getBattleFieldWidth() -200, getBattleFieldHeight() - 200);
        limite=new Rectangle2D.Double(160, 160, getBattleFieldWidth()- 320, getBattleFieldHeight() - 320);
        limite2=new Rectangle2D.Double(40, 40, getBattleFieldWidth() -80, getBattleFieldHeight() - 80);
        limite3=new Rectangle2D.Double(28, 28, getBattleFieldWidth() -56, getBattleFieldHeight() - 56);

        // Robot main loop
        while(true) {
            setTurnRadarRight(radar);
            ahead(90);

        }
    }

public int Mod(){
            double x=Math.random()*100+1,y=getEnergy();
			
            if(y<100){
                    if(x<(y/2)-30){//teste off
                        return 1;
                    }else if(x<(y/3)-(40/3)+20){//teste off bal
                        return 2;
                    }else if(x<(2*y/7)-(60/7)+40){//teste bal
                        return 3;
                    }else if(x<(2*y/9)-(20/9)){//teste def bal
                        return 4;
                    }else{//teste def
                        return 5;
                    }
            }else{
                if(x<20){//teste off
                        return 1;
                    }else if(x<40){//teste off bal
                        return 2;
                    }else if(x<60){//teste bal
                        return 3;
                    }else if(x<80){//teste def bal
                        return 4;
                    }else{//teste def
                        return 5;
                    }
            }
}

public void mira(double aux) {
    double A=getHeading()+aux-getGunHeading();
    if (!(A > -180 && A <= 180)) {
        while (A <= -180) {
            A += 360;
        }
        while (A > 180) {
            A -= 360;
        }
    }
    setTurnGunRight(A);
}

public void atira(){
    int Mod = Mod();
         if(Mod == 5){
         	tiro=Rules.MIN_BULLET_POWER;
        }else{
        	if(Mod == 4){
                tiro=Rules.MIN_BULLET_POWER+0.5;
			}
            if(Mod == 3){ 
            tiro= getEnergy()%Rules.MAX_BULLET_POWER;
			}	
            if(Mod == 2){
             	tiro=Rules.MAX_BULLET_POWER-1;
             }
             if(Mod == 1){
                tiro=Rules.MAX_BULLET_POWER;
			}
		}
		fireBullet(tiro);
}
    /*** onScannedRobot: What to do when you see another robot*/
    public void onScannedRobot(ScannedRobotEvent e) {
        radar=-radar;
        setTurnRadarRight(radar);
        angIni=e.getBearing();
        setTurnRight(angIni+90-(gira*direcao));
        setAhead(80*direcao);

        inimigoDist=e.getDistance();
        anguloInimigo=e.getBearingRadians();
        angIni=e.getBearing();
		marcarInimigo= new Point2D.Double(Math.sin(getHeadingRadians()+anguloInimigo)*inimigoDist+x,Math.cos(getHeadingRadians()+anguloInimigo)*inimigoDist+y);

        double ang=e.getBearingRadians()+getHeadingRadians();
        double distancia=Math.max(Math.min(400/e.getDistance(),3),0.1);
        double mira=senCos(Math.asin(0.8*e.getVelocity()/(20-3*distancia)*Math.sin(e.getHeadingRadians()-ang))+ang-getGunHeadingRadians());
        setTurnGunRightRadians(mira);
        mira(e.getBearing());
        atira();
		double posiX=getX(),posiY=getY();
        if(e.getDistance()>300){
            gira=15;
        }
        else if(e.getDistance()<100){
            gira=-15;
        }
        else{
            gira=0;                           
		}
        if(!limite.contains(posiX,posiY)){
            gira=25;
        }
        if(!campo.contains(posiX,posiY)){
            gira=40;
        }
        if(!limite2.contains(posiX,posiY)){
            gira=60;
        }
        if(!limite3.contains(posiX,posiY)){
            gira=90;
        }
		scan();
    }
    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
	public void onHitByBullet(HitByBulletEvent e) {
	    direcao=-direcao;
	}

	public void onHitRobot(HitRobotEvent e) {
	     mira(e.getBearing());
	     fire(1);
	}
	public double senCos(double _ang){
        return Math.atan2(Math.sin(_ang), Math.cos(_ang));
//pegar o seno e cos do angulo e transforma-los em uma coordenada polar
    }
	
	public void onWin(WinEvent e){
		setBodyColor(new Color(255 ,215 ,0));
		setGunColor(new Color(255 ,215 ,0));
		setRadarColor(new Color(205 ,173 ,0));
		setScanColor(new Color(255, 255,255));
		setBulletColor(new Color(205 ,149 ,12));
		ahead(0);
		setTurnRadarRight(7200);
		setTurnRight(720);
		turnGunRight(720);
	} 
}
