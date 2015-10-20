/* * * * * * * * * * * * * * * *
 * Zadanie na predmet Objektove Programovanie
 *
 * Stefan Ciberaj, ZS 2015/2016
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
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

import java.util.List;
import java.util.ArrayList;

/**
 * Ide to vybuchnut.
 * 
 * @author Steve
 */
public class Reactor extends AbstractAdvancedActor implements Switchable, Repairable
{
    /************************************\
    |* Week 2, Reactor
    \************************************/
    
    //Konstanty
    //public static final int MAX_TEMPERATURE = 5000; //Uz toto obmedzenie nefunguje. Je implicitne dane cez MAX_DAMAGE.
    public static final int CRITICAL_TEMP = 2000;
    public static final int MIN_TEMPERATURE = 0;
    public static final int MIN_DAMAGE = 0;
    public static final int CRITICAL_DAMAGE = 50;
    public static final int MAX_DAMAGE = 100;
    //public static final int ZAPNUTE = 1;
    //public static final int VYPNUTE = 0;
    
    //Stavy
    private int temperature;
    //private boolean temperatureBonus;
    private int damage;
    private boolean on;
    
    //Informacne atributy
    private String manufacturer;
    private int yearOfProduction;
    
    //Objekty
    private List<EnergyConsumer> devices;
    
    public Reactor()
    {
	//Inicializacia
        this.temperature=MIN_TEMPERATURE;
        //this.temperatureBonus=false;
        this.damage=MIN_DAMAGE;
        this.on=false;
        this.manufacturer="";
        this.yearOfProduction=1;
        
	//Objekty
	this.devices = new ArrayList</*EnergyConsumer*/>();
	
	//Animacie
	addAnimation("reactor",80,80,100);
        addAnimation("reactor_on",80,80,100);
        addAnimation("reactor_hot", 80, 80, 50);
        addAnimation("reactor_broken", 80, 80, 100);
        updateAnimation();
    }
    
    public Reactor(String manufacturer, int yearOfProduction) throws ReactorException
    {
        this();
	
        //Priradenie novych parametrov
        setManufacturer(manufacturer);
        setYearOfProduction(yearOfProduction);
    }
    
    class ReactorException extends Exception
    {
        public static final String YEAR_LESS_ZERO = "Error. Reactor cannot be born when Christ was, or before that.";

        public ReactorException(String msg)
        {
            super(msg);
        }
    }
    
    //Automaticka volba spravnej animacie
    private void updateAnimation()
    {
        //Zniceny reaktor svoju animaciu nezmeni
        if(getDamage()==MAX_DAMAGE)
        {
            updateAnimation("reactor_broken");
        }
        else if(isOn())
        {
            if(getTemperature()>=4000/* && getDamage()<MAX_DAMAGE*/)
            {
                updateAnimation("reactor_hot");
                updateAnimationSpeed();
            }
            else if(getTemperature()<4000/* && getDamage()<MAX_DAMAGE*/)
            {
                updateAnimation("reactor_on");
                updateAnimationSpeed();
            }
        }
        else
        {
            updateAnimation("reactor");
        }
    }
    
    private void setYearOfProduction(int year) throws ReactorException
    {
        if(year<=0)
        {
            throw new ReactorException(ReactorException.YEAR_LESS_ZERO);
        }
        this.yearOfProduction=year;
    }
    
    public int getYearOfProduction()
    {
        return this.yearOfProduction;
    }
    
    private void setManufacturer(String manufacturer)
    {
        this.manufacturer=manufacturer;
    }
    
    public String getManufacturer()
    {
        return this.manufacturer;
    }
    
    //Nastavi novu rychlost aktualnej animacie
    private void updateAnimationSpeed()
    {
        if(getDamage()>CRITICAL_DAMAGE && getDamage()<MAX_DAMAGE)
        {
            setAnimationSpeed(MAX_DAMAGE-getDamage());
        }
        else
        {
            setAnimationSpeed(getAnimationSpeed());
        }
    }
    
    public int getDamage()
    {
        return this.damage;
    }
    
    private void setDamage(int damage)
    {
        if(damage>MAX_DAMAGE)
        {
            this.damage = MAX_DAMAGE;
        }
        else if(damage<MIN_DAMAGE)
        {
            this.damage = MIN_DAMAGE;
        }
        else
        {
            this.damage = damage;
        }
	updateAnimation();
	updateElectricity();
    }
    
    public int getTemperature()
    {
        return this.temperature;
    }
    
    public void increaseTemperature(int inc)
    {
        //Ak nie je vstupny parameter zaporny a reaktor je zapnuty
        if(inc>0 && isOn())
        {
	    //Zvysi teplotu o vstup*modifier pri zohladneni vsetkych podmienok
            /*for(int i=0;i<inc;i++)
            {*/
		//poskodenie nad CRITICAL_DAMAGE% je 2x teplota
		if(getDamage()<=CRITICAL_DAMAGE)
		{
		    //Rychlost 1x
		    setTemperature(getTemperature()+inc);
		    updateDamage();
		}
		else
		{
		    //Rychlost 2x
		    setTemperature(getTemperature()+2*inc);
		    updateDamage();
		}
            /*}*/
        }
    }
    
    public void decreaseTemperature(int dec)
    {
        //Ak nie je vstupny parameter zaporny a ak je predikat splneny
        if(dec>0 && isOn())
        {
	    //Ak este reaktor funguje, znizi teplotu o vstup*modifier pri zohladneni vsetkych podmienok
            /*for(int i=0;i<dec;i++)
            {*/
		if(getDamage()<MAX_DAMAGE && getDamage()>=CRITICAL_DAMAGE)
		{
		    //Rychlost znizovania teploty nad CRITICAL_DAMAGE% je 0.5x
		    setTemperature(getTemperature()-dec/2);
		    /*if(this.temperatureBonus)
		    {
			setTemperature(getTemperature()-1);
			updateDamage();
			this.temperatureBonus=false;
		    }
		    else
		    {
			this.temperatureBonus=true;
		    }*/
		}
		else if(getDamage()<MAX_DAMAGE)
		{
		    //Rychlost znizovania teploty pod CRITICAL_DAMAGE% je 1x
		    setTemperature(getTemperature()-dec);
		    updateDamage();
		}
            /*}*/
        }
    }
    
    //Aktualizovanie damage podla aktualnej teploty
    private void updateDamage()
    {
        if(getTemperature()>CRITICAL_TEMP)
        {
            int newDamage = (getTemperature()-CRITICAL_TEMP)/30;
            if(getDamage()<newDamage)
            {
                setDamage(newDamage);
            }
        }
	
	//Operacie, ktore je nutne vykonat po kazdej zmene teploty
        if(getDamage()>=MAX_DAMAGE)
        {
            //Reaktor sa z bezpecnostnych dovodov po vybuchu vypne
            turnOff();
        }
	
	updateAnimation();
    }
    
    //Funkcia urcena na vnutorne rutiny triedy, ktore menia teplotu na konkretnu hodnotu
    //Jej hlavnou ulohou je znicit reaktor
    private void setTemperature(int temperature)
    {
	/*
	//ODSTRANENA FUNKCIONALITA: Obmedzenie max. teploty na 5000
	//Nastavenie novej teploty
	if(temperature>=MAX_TEMPERATURE)
	{
	    //Ak je teplota vacsia nez maximalna, tak bude maximalna
	    this.temperature=MAX_TEMPERATURE;
	}
	else
	*/
	    
	if(temperature<=MIN_TEMPERATURE)
	{
	    //Ak je teplota mensia nez minimalna, tak bude minimalna
	    this.temperature=MIN_TEMPERATURE;
	    //this.temperatureBonus=false;
	}
	else
	{
	    //Kym je teplota medzi maximalou a minimalnou, tak je platna
	    this.temperature=temperature;
	}

        updateAnimation();
    }
    
    public boolean isServiceNeeded()
    {
	return getTemperature()>3000 && getDamage()>50;
    }
    
    /************************************\
    |* Week 3 Hammer, Controller funkcie
    \************************************/
    
    @Override
    public void turnOn()
    {
        this.on=true;
        updateAnimation();
	updateElectricity();
    }
    
    @Override
    public void turnOff()
    {
        this.on=false;
        updateAnimation();
	updateElectricity();
    }
    
    @Override
    public boolean isOn()
    {
        return this.on;
    }
    
    private void decreaseDamage(int damage)
    {
	//Vstup musi byt nezaporny a predikat splneny
        if(damage>0 && getDamage()<100)
        {
	    //Linearna funkcia ktora vzdy vypocita teplotu podla posunu
	    int newTemperature = 30 *(getDamage()-damage) + CRITICAL_TEMP;

	    //Aktualizuje teplotu. Ak by mala ist teplota pod nulu, tak sa to osetri, inak sa nastavi
	    if(newTemperature<0)
	    {
		setTemperature(0);
	    }
	    else
	    {
		if(newTemperature<getTemperature())
		{
		    setTemperature(newTemperature);
		}
	    }
	    
	    //Znizi damage
            setDamage(getDamage()-damage);
        }
    }
    
    @Override
    public void repair(AbstractTool hammer)
    {
        //Ak sa ma pouzit kladivo, tak ho pouzijem. Ak sa kladivo uspesne pouzije
        if(hammer != null && hammer instanceof Hammer/*Hammer.class.isInstance(hammer)*/ && hammer.use())
        {
	    //Znizi hodnotu damage o 50, cim nastavi novu teplotu.
	    decreaseDamage(50);
	}
    }
    
    //Funkcia na aktualizovanie stavu dodavky elektriny pripojenych zariadeni k reaktoru.
    //Je potrebne zavolat ju stale po zmene stavu zapnutia reaktora.
    private void updateElectricity()
    {
	//Ak je pripojene zariadenie, zapnem mu dodavku elektriny v pripade, ze je reaktor zapnuty a nie je pokazeny.
	for(EnergyConsumer device: this.devices)
	{
	    device.setElectricityFlow(isOn()&&(getDamage()<100));
        }
    }
    
    /************************************\
    |* Week 4 Cooler, act()
    \************************************/
    
    //Samovolne prehrievanie reaktora
    @Override
    public void act()
    {
	if(isOn() && (getDamage()<MAX_DAMAGE))
	{
	    increaseTemperature(1);
	}
    }
    
    /************************************\
    |* Week 5 Switchable
    \************************************/
    
    //Pripojenie zariadenia odoberajuceho elektrinu na reaktor
    //Definovana cinnost pri vstupe null: nezmenit nic
    public void addDevice(EnergyConsumer device)
    {
	//V pripade poziadavky na pripojenie svetla pripojim nove zariadenie a aktualizujem ho
        if(device!=null && !this.devices.contains(device))
	{
	    this.devices.add(device);
	    updateElectricity();
        }
    }
    
    public void removeDevice(EnergyConsumer device)
    {
	//Ak je pripojene zariadenie, odpojim mu dodavku elektriny a odpojim ho.
	if(device!=null && this.devices.contains(device))
        {
	    device.setElectricityFlow(false);
	    this.devices.remove(device);
	}
    }
}
