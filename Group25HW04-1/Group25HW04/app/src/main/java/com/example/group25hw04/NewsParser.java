package com.example.group25hw04;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by nalin on 2/22/2018.
 */

public class NewsParser {

    public static class NewsPullParser
    {
        static public ArrayList<News> parseNews(InputStream inputStream) throws XmlPullParserException, IOException {

            ArrayList<News> news=new ArrayList<News>();

              News newsarticle=new News();
            XmlPullParserFactory parserFactory=XmlPullParserFactory.newInstance();

            XmlPullParser parser=parserFactory.newPullParser();
            parser.setInput(inputStream,"UTF-8");

            int event=parser.getEventType();

            while(event!=XmlPullParser.END_DOCUMENT)
            {
                switch (event)
                {
                    case XmlPullParser.START_TAG:


                        if(parser.getName().equals("item")) {

                            parser.nextTag();
                            newsarticle = new News();
                            if (parser.getName().equals("title")) {

                                newsarticle.newsTitle = parser.nextText().trim();
                            }
                        }
                        else if (parser.getName().equals("description")) {
                            newsarticle.newsDescription = parser.nextText().trim().replaceAll("<[^>]*>", "");
                           // newsarticle.newsDescription = parser.nextText().trim().replaceAll("&lt;.+?&gt;", "");
                            }
                            else if (parser.getName().equals("media:group")) {
                                parser.nextTag();
                                if (parser.getName().equals("media:content")) {
                                    newsarticle.newsImage = parser.getAttributeValue(null, "url");
                                }
                            }
                            else if(parser.getName().equals("media:thumbnail"))
                           {
                               newsarticle.newsImage = parser.getAttributeValue(null, "url");
                           }

                            else if (parser.getName().equals("pubDate")) {
                                newsarticle.publishedat = parser.nextText().trim();

                            }




                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item"))
                        {
                            news.add(newsarticle);
                        }
                          break;
                        default:
                            break;
                }

                event=parser.next();
            }


            return  news;

        }
    }
}
