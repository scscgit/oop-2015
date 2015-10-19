/* * * * * * * * * * * * * * * *
 * Zadanie na predmet Objektové Programovanie
 *
 * Štefan Ciberaj, ZS 2015/2016
 * Technická univerzita v Košiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Voľný softvér, Open-Source GNU GPL v3+
 * Všeobecná verejná licencia. Program je dovolené voľne šíriť a upravovať.
 * Upravený program / časť programu môže ktokoľvek využiť ako na osobné,
 * tak aj komerčné účely, ale nemôže ho vydať s vlastným copyrightom,
 * ktorý nie je kompatibilný s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sk.tuke.oop.aliens;

import java.util.ArrayList;

/**
 * Vyznam AdvancedActora:
 *  Pridava vseobecne premenne a funkcie v ramci projektu, ktore by sa nachadzali v kazdej podtriede AbstractActor, takze tuto triedu nahradza.
 * 
 * Zoznam doplnkov:
 * 1. Pridana podpora cachingu Animacii
 * 
 * @author Steve
 */
public abstract class AdvancedActor extends sk.tuke.oop.aliens.actor.AbstractActor
{
    /************************************\
    |* Week 3, AdvancedActor by Steve
    \************************************/
    
    private ArrayList<Animacia> animacie; //Pole animacii
    private Animacia currentAnimacia; //Aktualne zapnuta animacia
    private int animacie_index; //Index urcujuci nasledujucu animaciu na vznik, tj. pocet existujucich animacii
    
    //Objekt na ulozenie animacie
    public class Animacia
    {
	private sk.tuke.oop.framework.Animation normalAnimation;
	private int index;
        private String png;
        private int x;
        private int y;
        private int speed;
	private boolean pingpong;
	private boolean looping;

        public Animacia(String png, int x, int y, int speed, boolean pingpong, boolean looping)
        {
	    if(pingpong&&!looping){System.out.println("Warning: "+png+"has pingpong without looping, expecting crash!");}; //Pomocka pre pripad, ze nabuduce nebudem vediet preco to crashlo
	    this.normalAnimation = new sk.tuke.oop.framework.Animation("resources/images/"+png+".png", x, y, speed);
	    normalAnimation.setPingPong(pingpong);
	    normalAnimation.setLooping(looping);
            this.png = png;
            this.x = x;
            this.y = y;
            this.speed = speed;
	    this.pingpong = pingpong;
	    this.looping = looping;
	    
	    this.index = animacie_index;
	    animacie_index++;
        }
	
	//Implicitne parametre konstruktora
	public Animacia(String png, int x, int y, int speed)
        {
	    this(png, x, y, speed, true, true);
        }
	
	//Semanticka rovnost dvoch animacii
	//V tomto pripade je definovana rovnostou vsetkych clenov. (Trivia: stara verzia to definovala na zaklade rovnakeho stringu png textury)
	public boolean equals(Animacia a)
	{
	    return (this.png.equals(a.png) && this.x == a.x && this.y == a.y && this.speed == a.speed && this.pingpong == a.pingpong && this.looping == looping);
	}
	
	public int getSpeed()
        {
            return this.speed;
        }
	
	@Override
        public String toString()
        {
            return this.png;
        }
    }
    
    public AdvancedActor()
    {
        currentAnimacia = new Animacia("",0,0,0); //Defaultna null animacia, ktorou sa ziadna skutocna animacia nesmie rovnat
        animacie = new ArrayList<>(); //Inicializacia pola animacii
	animacie_index = 0; //Animacie zacnu od cisla indexu 0, po inicializacii existuje 0 animacii
    }
    
    //Prida novu animaciu, pripadne vrati index existujucej, ktora je semanticky ekvivalentna novej
    protected int addAnimation(String png, int x, int y, int speed, boolean pingpong, boolean looping)
    {
	Animacia novaAnimacia = new Animacia(png, x, y, speed, pingpong, looping);
        for(Animacia i: animacie)
        {
            //ak sa i-ta animacia semanticky rovna novej animacii, tak uz existuje
            if(i.equals(novaAnimacia))
            {
                //vratim jej index a usetrim miesto v pamati tym, ze ju nemusim znovu vyrabat
                return i.index;
            }
        }
        //Ak nebola najdena, tak ju pridam do pola
        animacie.add(novaAnimacia);
	//Vraciam novy index
	return novaAnimacia.index;
    }
    
    //Implicitne parametre
    protected int addAnimation(String png, int x, int y, int speed)
    {
	return addAnimation(png, x, y, speed, true, true);
    }
    
    //Nastavi novu rychlost aktualnej animacie, bez jej aktualizacie v poli animacii. Nezmeni premenne aktualnej animacie.
    protected void setAnimationSpeed(int speed)
    {
        currentAnimacia.normalAnimation.setDuration(speed);
    }
    
    //Pokial bola zmenena rychlost animacie, moze byt potrebne ziskat naspat defaultnu rychlost animacie.
    protected int getAnimationSpeed()
    {
        return currentAnimacia.speed;
    }
    
    //Nastavi opakovanie animacie, bez jej aktualizacie v poli animacii. Nezmeni premenne aktualnej animacie.
    protected void setAnimationLooping(boolean on)
    {
        currentAnimacia.normalAnimation.setLooping(on);
    }
    
    //Prepne aktualnu animaciu na novu, ktora uz existuje v poli animacie, pricom ju definuje nazvom png.
    //Tato funkcia funguje iba na vyhladavanie generickych animacii. V pripade odlisnych parametrov je potrebne spustat ich pomocou indexu.
    protected void updateAnimation(String png)
    {
        //Ak nastala zmena animacie
        if(!this.currentAnimacia.png.equals(png))
        {
            //Najdem danu animaciu v zozname animacii
            for(Animacia i: animacie)
            {
                //ak ma i-ta animacia meno png suboru = png
                if(i.png.equals(png))
                {
                    //tak jej animaciu spustim a nastavim ju na novu aktualnu animaciu
                    runAnimacia(i);
                    return;
                }
            }
	    System.out.println("Textura "+png+" nebola najdena.");
	    //Ak nebola najdena, tak... asi nic no. Mohol by som hodit aj chybu, alebo dat obrazok na chybovy.
        }
    }
    
    //Prepne aktualnu animaciu na novu, na zaklade jej indexu v ramci aktualneho objektu/triedy
    protected void updateAnimation(int index)
    {
	//Ak nastala zmena animacie
        if(this.currentAnimacia.index != index)
        {
            //Najdem danu animaciu v zozname animacii
            for(Animacia i: animacie)
            {
                //ak ma i-ta animacia meno png suboru = png
                if(i.index == index)
                {
                    //tak jej animaciu spustim a nastavim ju na novu aktualnu animaciu
                    runAnimacia(i);
                    return;
                }
            }
	    System.out.println("Textura s indexom "+index+" nebola najdena.");
	    //Ak nebola najdena, tak... asi nic no. Mohol by som hodit aj chybu, alebo dat obrazok na chybovy.
        }
    }
    
    private void runAnimacia(Animacia a)
    {
	setAnimation(a.normalAnimation);
	currentAnimacia = a;
    }
    
    /* Obsolete funkcie, ktore nemaju uplatnenie
    protected Animacia findAnimationIndex(int index)
    {
	for(Animacia a: animacie)
	{
	    if(a.index == index)
	    {
		return a;
	    }
	}
	return null;
    }
    
    protected void runAnimationIndex(int index)
    {
	Animacia a = findAnimationIndex(index);
	setAnimation(a.normalAnimation);
	currentAnimacia = a;
    }
    */
}
