package it.unipv.ingsfw.JavaBeats.model.playable.audio;
import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidAudioException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class JBAudioTest{

  Metadata metadata=new Metadata();

  @Before
  public void initTest(){
    try{
      File f=new File("Test/resources/Faint.mp3");
      FileInputStream fileInputStream=new FileInputStream(f);
      ContentHandler handler=new DefaultHandler();
      Parser parser=new Mp3Parser();
      ParseContext parseContext=new ParseContext();
      parser.parse(fileInputStream, handler, metadata, parseContext);
      fileInputStream.close();
    }catch(TikaException | IOException | SAXException e){
      fail();
    }//end-try
  }

  @Test
  public void testMetadataAudioAllMetadata(){
    boolean result=true;
    try{
      CollectionManagerFactory.getInstance().getCollectionManager().checkMetadata(metadata);
    }catch(InvalidAudioException i){
      result=false;
    }//end-try
    assertTrue(result);
  }

  @Test
  public void testMetadataAudioNoGenre(){
    boolean result=true;
    try{
      metadata.set("xmpDM:genre", null);

      CollectionManagerFactory.getInstance().getCollectionManager().checkMetadata(metadata);
    }catch(InvalidAudioException i){
      result=false;
    }//end-try
    assertFalse(result);
  }

  @Test
  public void testMetadataAudioNoDuration(){
    boolean result=true;
    try{
      metadata.set("xmpDM:duration", null);

      CollectionManagerFactory.getInstance().getCollectionManager().checkMetadata(metadata);
    }catch(InvalidAudioException i){
      result=false;
    }//end-try
    assertFalse(result);
  }

  @Test
  public void testMetadataAudioNoGenreAndDuration(){
    boolean result=true;
    try{
      metadata.set("xmpDM:genre", null);
      metadata.set("xmpDM:duration", null);

      CollectionManagerFactory.getInstance().getCollectionManager().checkMetadata(metadata);
    }catch(InvalidAudioException i){
      result=false;
    }//end-try
    assertFalse(result);
  }
}