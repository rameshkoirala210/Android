package com.example.jsonpractice;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PersonsXMLParser extends DefaultHandler {
    Person person;
    ArrayList<Person> persons = new ArrayList<>();
    final String TAG = "TAG";
    StringBuilder buffer = new StringBuilder();

    public ArrayList<Person> parse(InputStream inputStream) throws IOException, SAXException {
        Xml.parse(inputStream, Xml.Encoding.UTF_8, this);
        return persons;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        Log.d(TAG, "startDocument: ");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.d(TAG, "endDocument: ");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(localName.equals("persons")){
            persons = new ArrayList<>();
        } else if(localName.equals("person")){
            person = new Person();
            person.setId(attributes.getValue("id"));

        } else if(localName.equals("adress")){
            person.setAdress(new Adress());
        }
        Log.d(TAG, "startElement: " + localName);
        buffer.setLength(0);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if(localName.equals("person")){
            persons.add(person);
        } else if(localName.equals("name")){
            person.setName(buffer.toString());
        } else if (localName.equals("age")){
            person.setAge(buffer.toString());
        } else if(person.adress != null){
            if(localName.equals("line1")){
                person.getAdress().setLine1(buffer.toString());
            } else if (localName.equals("city")){
                person.getAdress().setCity(buffer.toString());
            } else if (localName.equals("state")){
                person.getAdress().setState(buffer.toString());
            } else if (localName.equals("zip")){
                person.getAdress().setZip(buffer.toString());
            }
        }
        Log.d(TAG, "endElement: " + localName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        buffer.append(ch, start, length);
        Log.d(TAG, "characters: " + buffer.toString());
    }
}
