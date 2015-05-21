package collections

object Google {
  println("Hey Google")                           //> Hey Google

  import scala.io._
  val content = Source.fromURL("http://www.blick.ch")(Codec.UTF8).getLines.mkString
                                                  //> content  : String = <!DOCTYPE html>        <html prefix="fb: http://ogp.me/n
                                                  //| s/fb# og: http://ogp.me/ns#" lang="de">        <head>        <title>Blick | 
                                                  //| Aktuelle Nachrichten aus der Schweiz und aller Welt</title><meta http-equiv=
                                                  //| "Content-Type" content="text/html; charset=utf-8" /><meta name="description"
                                                  //|  content="Lesen Sie täglich aktuelle Nachrichten aus der Schweiz und der ga
                                                  //| nzen Welt, aus Sport, People, Wirtschaft und Lifestyle. Der Blick ist die gr
                                                  //| össte Schweizer News-Site." />        <meta name="keywords" content="Blick.
                                                  //| ch, aktuelle Nachrichten, Zeitung online, Informationen, lesen, News, Home" 
                                                  //| />        <meta name="news_keywords" content="Blick.ch, aktuelle Nachrichten
                                                  //| , Zeitung online, Informationen, lesen, News, Home" />                <meta 
                                                  //| name="author" content="Blick" /><meta name="robots" content="index,follow" /
                                                  //| ><meta name="robots" content="noodp" /><meta name="robots" content="noarchiv
                                                  //| e" /><meta name="cXensePar
                                                  //| Output exceeds cutoff limit.
  
   val htmlWord = Set(
     "class", "div", "http", "span", "href", "www", "html", "img",
     "title", "src", "alt", "width", "height", "strong", "clearfix",
     "target", "style", "script", "type", "_blank", "item", "last",
     "text", "javascript", "middot", "point", "display", "none")
                                                  //> htmlWord  : scala.collection.immutable.Set[String] = Set(span, point, style,
                                                  //|  img, _blank, height, div, text, strong, clearfix, last, alt, middot, src, c
                                                  //| lass, target, script, title, type, html, href, none, http, width, javascript
                                                  //| , display, www, item)
                                                  
  val words = content.split("\\W")                //> words  : Array[String] = Array("", "", DOCTYPE, html, "", "", "", "", "", ""
                                                  //| , "", "", "", html, prefix, "", fb, "", http, "", "", ogp, me, ns, fb, "", o
                                                  //| g, "", http, "", "", ogp, me, ns, "", "", lang, "", de, "", "", "", "", "", 
                                                  //| "", "", "", "", "", head, "", "", "", "", "", "", "", "", "", title, Blick, 
                                                  //| "", "", Aktuelle, Nachrichten, aus, der, Schweiz, und, aller, Welt, "", titl
                                                  //| e, "", meta, http, equiv, "", Content, Type, "", content, "", text, html, ""
                                                  //| , charset, utf, 8, "", "", "", "", meta, name, "", description, "", content,
                                                  //|  "", Lesen, Sie, t, glich, aktuelle, Nachrichten, aus, der, Schweiz, und, de
                                                  //| r, ganzen, Welt, "", aus, Sport, "", People, "", Wirtschaft, und, Lifestyle,
                                                  //|  "", Der, Blick, ist, die, gr, sste, Schweizer, News, Site, "", "", "", "", 
                                                  //| "", "", "", "", "", "", "", "", "", meta, name, "", keywords, "", content, "
                                                  //| ", Blick, ch, "", aktuelle, Nachrichten, "", Zeitung, online, "", Informatio
                                                  //| nen, "", lesen, "", News
                                                  //| Output exceeds cutoff limit.
  val important = words.filter(w => w.size > 3).filter(w => !htmlWord(w))
                                                  //> important  : Array[String] = Array(DOCTYPE, prefix, lang, head, Blick, Aktue
                                                  //| lle, Nachrichten, Schweiz, aller, Welt, meta, equiv, Content, Type, content,
                                                  //|  charset, meta, name, description, content, Lesen, glich, aktuelle, Nachrich
                                                  //| ten, Schweiz, ganzen, Welt, Sport, People, Wirtschaft, Lifestyle, Blick, sst
                                                  //| e, Schweizer, News, Site, meta, name, keywords, content, Blick, aktuelle, Na
                                                  //| chrichten, Zeitung, online, Informationen, lesen, News, Home, meta, name, ne
                                                  //| ws_keywords, content, Blick, aktuelle, Nachrichten, Zeitung, online, Informa
                                                  //| tionen, lesen, News, Home, meta, name, author, content, Blick, meta, name, r
                                                  //| obots, content, index, follow, meta, name, robots, content, noodp, meta, nam
                                                  //| e, robots, content, noarchive, meta, name, cXenseParse, pageclass, content, 
                                                  //| frontpage, meta, property, content, blick, link, canonical, blick, link, app
                                                  //| le, touch, icon, blick, resources, 20150428, ver1, icon, precomposed, image,
                                                  //|  link, shortcut, icon, b
                                                  //| Output exceeds cutoff limit.
  val grouped = important .groupBy(w => w)        //> grouped  : scala.collection.immutable.Map[String,Array[String]] = Map(Deckel
                                                  //|  -> Array(Deckel, Deckel, Deckel, Deckel), Wert -> Array(Wert, Wert, Wert, W
                                                  //| ert, Wert, Wert), crop3727489 -> Array(crop3727489), widget_standardteaser -
                                                  //| > Array(widget_standardteaser, widget_standardteaser, widget_standardteaser)
                                                  //| , Terror -> Array(Terror, Terror, Terror, Terror, Terror, Terror, Terror, Te
                                                  //| rror), prop17 -> Array(prop17), looks -> Array(looks, looks), alpha -> Array
                                                  //| (alpha, alpha, alpha, alpha, alpha, alpha, alpha, alpha, alpha), Bolero -> A
                                                  //| rray(Bolero, Bolero), Limmatstrasse -> Array(Limmatstrasse), sechs -> Array(
                                                  //| sechs), 3282816046 -> Array(3282816046), Advertisement -> Array(Advertisemen
                                                  //| t), community_article_comments_default_3742508 -> Array(community_article_co
                                                  //| mments_default_3742508), essen -> Array(essen, essen), w139 -> Array(w139, w
                                                  //| 139, w139, w139, w139, w139), Milliardaer -> Array(Milliardaer), Siegerstras
                                                  //| se -> Array(Siegerstrass
                                                  //| Output exceeds cutoff limit.
  val list = grouped.mapValues(v => v.size).toList//> list  : List[(String, Int)] = List((Deckel,4), (Wert,6), (crop3727489,1), (w
                                                  //| idget_standardteaser,3), (Terror,8), (prop17,1), (looks,2), (alpha,9), (Bole
                                                  //| ro,2), (Limmatstrasse,1), (sechs,1), (3282816046,1), (Advertisement,1), (com
                                                  //| munity_article_comments_default_3742508,1), (essen,2), (w139,6), (Milliardae
                                                  //| r,1), (Siegerstrasse,1), (article1740258,1), (gelernt,2), (nbsp,46), (740109
                                                  //| 919,1), (Augen,5), (topteaser,20), (Dorf,4), (Lesen,2), (community_article_c
                                                  //| omments_default_3734150,1), (Miniteaser,1), (hatten,4), (blick_tile,1), (850
                                                  //| 2641050,1), (sentiert,1), (3944312,1), (userDataLoged,1), (Kreuzwortr,5), (f
                                                  //| ashion,6), (lieb,1), (community_article_comments_default_3742582,1), (Strass
                                                  //| enlaterne,1), (knapp,1), (Seite,3), (Auch,1), (addClass,13), (Lieblingshit,3
                                                  //| ), (Sorgen,10), (Bundesanwalt,5), (3509044,3), (id3741119,1), (Klum,5), (Sie
                                                  //| g,4), (Abstimmungskampf,1), (4281425630,1), (Schweiz,51), (staendchen,1), (v
                                                  //| iertelfinal,3), (farbsch
                                                  //| Output exceeds cutoff limit.
  val top10 = list.sortBy(p => p._2).reverse.take(10)
                                                  //> top10  : List[(String, Int)] = List((blick,718), (news,249), (sport,121), (i
                                                  //| ncoming,106), (life,86), (chorizontal,84), (schweiz,74), (people,71), (Blick
                                                  //| ,62), (links,56))
   
}