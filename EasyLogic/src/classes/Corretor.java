package classes;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Corretor {
	private static Corretor corretor;
	
	public static Corretor getInstance(){
		if(corretor == null)
			corretor = new Corretor();
		
		return corretor;
	}
	
	public String corrigirHtml(String txtCode) {
		String corrigido="", erro = "";
		boolean Variaveis=false, Inicio=false, Fim = false, jaDeuErro = false;
		
		ArrayList<String> booleanas = new ArrayList<String>();
		ArrayList<String> inteiras = new ArrayList<String>(); 
		ArrayList<String> strings = new ArrayList<String>(); 
		ArrayList<String> chars = new ArrayList<String>(); 
		ArrayList<String> doubles = new ArrayList<String>(); 
	
		String linhas[] = txtCode.split("\n");
		for (int i = 0; i < linhas.length; i++) {
			erro = "";
			if(!jaDeuErro){
				if(!linhas[i].equals("") && !Variaveis && !linhas[i].replaceAll("\t", "").replaceAll(" ", "").equalsIgnoreCase("")){
					if(linhas[i].equalsIgnoreCase("Variaveis")){
						Variaveis=true;
					}else{
						erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: linha 'Variaveis' faltando"+"</font></b>";
						jaDeuErro=true;			
					}
				}else if(!linhas[i].equals("") && !Inicio && !linhas[i].replaceAll("\t", "").replaceAll(" ", "").equalsIgnoreCase("")){
					if(linhas[i].equalsIgnoreCase("inicio")){
						Inicio = true;
					}else{
						String var[] = linhas[i].split(":");
						if(var.length == 2 && !var[0].replaceAll(" ", "").replaceAll("\t", "").equals("")){
							
							if(var[0].contains("real") || var[0].contains("inteiro") || var[0].contains("caractere") || var[0].contains("texto") || var[0].contains("logico")){
								erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: o nome de uma variavel nao pode ser igual o nome de um tipo de variavel"+"</font></b>";
								jaDeuErro=true;	
							}else{
								String tipo = var[1].replaceAll("\t", "").replaceAll(" ", "");
								if(!tipo.equalsIgnoreCase("real") && !tipo.equalsIgnoreCase("inteiro") && !tipo.equalsIgnoreCase("caractere") && !tipo.equalsIgnoreCase("texto") && !tipo.equalsIgnoreCase("logico")){
									erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: o tipo de variavel nao é valido"+"</font></b>";
									jaDeuErro=true;	
								}
							}
							
							for (int j = 0; j <= 176; j++) {
								if(linhas[i].contains((char)j+"")){
									erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: esta linha contem caracteres irregulares"+"</font></b>";
									jaDeuErro=true;	
									break;
								}
								
								if(j == 31 || j == 43 || j == 8)
									j++;
								if(j == 47)
									j+=11;
								if(j == 64 || j == 96)
									j+=25;
							}
							
							int virgulas=0;
							String aux0 ="";
							ArrayList<String> verificaVar = new ArrayList<String>();
							for (int j = 0; j < var[0].length(); j++) {
								if((int)var[0].charAt(j) != 9 && var[0].charAt(j) != ' ' && var[0].charAt(j) != ','){
									aux0+=var[0].charAt(j);
								}else{
									if(var[0].charAt(j) == ',')
										virgulas++;
									if(aux0.length() > 0){
										verificaVar.add(aux0);
									}
									aux0="";										
								}
							}
							if(!aux0.equals(""))
								verificaVar.add(aux0);
							
							for (int j = 0; j < verificaVar.size(); j++) {
								if(verificaVar.get(j).equalsIgnoreCase("")){
									verificaVar.remove(j);
								}
							}						
							if(virgulas+1 != verificaVar.size()){
								erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: esta faltando virgulas nesta linha"+"</font></b>";
								jaDeuErro=true;	
							}
							
							String aux1 =  var[0].replaceAll("\t", "").replaceAll(" ", "");
							
							for (int j = 0; j < aux1.length(); j++) {
								if(aux1.charAt(j) == ',' && (j == 0 || j == aux1.length()-1)){
									erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: esta sobrando virgulas nesta linha ou elas estao nos lugares errados"+"</font></b>";
									jaDeuErro=true;	
									break;
								}
								if(aux1.charAt(j) == ',' && j+1 != aux1.length() && aux1.charAt(j+1) == ','){
									erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: esta sobrando virgulas nesta linha"+"</font></b>";
									jaDeuErro=true;	
									break;
								}
							}
							
							String aux2[] = aux1.split(",");
							for (int j = 0; j < aux2.length; j++) {
								if(!aux2[j].equals("") && (int)aux2[j].charAt(0) >= 48 && (int)aux2[j].charAt(0) <= 57){
									erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: o nome de uma variavel nao pode iniciar com um numero"+"</font></b>";
									jaDeuErro=true;	
									break;
								}else{
									if(!inteiras.contains(aux2[j]) && !doubles.contains(aux2[j]) && !booleanas.contains(aux2[j]) && !strings.contains(aux2[j]) && !chars.contains(aux2[j])){
										String tipo = var[1].replaceAll("\t", "").replaceAll(" ", "");
										if(tipo.equals("inteiro")){
											inteiras.add(aux2[j]);
										
										}else if(tipo.equals("real")){
											doubles.add(aux2[j]);
										
										}else if(tipo.equals("texto")){
											strings.add(aux2[j]);
										
										}else if(tipo.equals("caractere")){
											chars.add(aux2[j]);
										
										}else if(tipo.equals("logico")){
											booleanas.add(aux2[j]);
										
										}
									}else{
										erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: nao é possivel declaras mais de uma variavel com o mesmo nome"+"</font></b>";
										jaDeuErro=true;	
										break;
									}
								}
							}
							
							

						}else{
							erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: nome ou tipo da variavel não foi declarado"+"</font></b>";
							jaDeuErro=true;	
						}
					}
				}else if(!linhas[i].equals("") && !Fim && !linhas[i].replaceAll("\t", "").replaceAll(" ", "").equalsIgnoreCase("")){
					if(linhas[i].equalsIgnoreCase("fim")){
						Fim = true;
					}else{
						if(!linhas[i].contains("(") && !linhas[i].contains("=")){
							erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: comando invalido"+"</font></b>";
							jaDeuErro=true;	
						}else{
							ArrayList<String> verificaComando = new ArrayList<String>();
							String concat = "", sinal ="";
							
							for (int j = 0; j < linhas[i].length(); j++) {
								if((int)linhas[i].charAt(j) != 9 && linhas[i].charAt(j) != ' ' && linhas[i].charAt(j) != '=' && linhas[i].charAt(j) != '('){
									concat+=linhas[i].charAt(j);
								}else{
									if(sinal.length() == 0 && linhas[i].charAt(j) == '=' ||linhas[i].charAt(j) == '(' ){
										sinal = linhas[i].charAt(j)+"";
										break;
									}
									
									if(concat.length() > 0)
										verificaComando.add(concat);
									if(verificaComando.size() > 1){
										erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: comando ou nome de variavel invalido"+"</font></b>";
										jaDeuErro=true;	
										break;
									}else
										concat="";
								}
							}
							if(concat.length() > 0)
								verificaComando.add(concat);
							if(verificaComando.size() > 1){
								erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: comando ou nome de variavel invalido"+"</font></b>";
								jaDeuErro=true;	
							}else if(verificaComando.size() == 1){
								if(sinal.equals("=")){
//									System.out.println(linhas[i].replaceAll("\t", "").replaceAll(" ", "").split("=").length);
									for (int j = 0; j < booleanas.size() || j < inteiras.size() || j < doubles.size() || j < strings.size() || j < chars.size(); j++) {
										if(!booleanas.contains(verificaComando.get(0)) && !inteiras.contains(verificaComando.get(0)) && !doubles.contains(verificaComando.get(0)) && !chars.contains(verificaComando.get(0)) && !strings.contains(verificaComando.get(0))){
											erro = "<b><font color='EF2C22'>"+linhas[i]+"---ERRO: nao foi possivel encontrar uma variavel com este nome"+"</font></b>";
											jaDeuErro=true;	
											break;
										}
									}
									
									
								}
							}
						}
					}
				}
			}
			if(jaDeuErro && !erro.equals(""))
				corrigido += erro.replaceAll("\n", "").replaceAll("<br/>", "")+"\n";
			else
				corrigido += linhas[i]+"\n";
		}

		String html = 	"<html>"
				+ 			"<head>"
				+ 				"<title></title>"
				+ 				"<style>"
				+ 					"body{font-family: 'Courier New'; color:'000000'; font-size:10px}"
				+ 				"</style>"
				+ 			"</head>"
				+ 			"<body>";
		if(!jaDeuErro){
			corrigido = corrigido.replaceAll("escreva", "<b><font color='781D9F'>escreva</font></b>");
			corrigido = corrigido.replaceAll("leia", "<b><font color='1BBC26'>leia</font></b>");
			corrigido = corrigido.replaceAll("real", "<i><font color='C3510A'>real</font></i>");
			corrigido = corrigido.replaceAll("texto", "<i><font color='C3510A'>texto</font></i>");
			corrigido = corrigido.replaceAll("caractere", "<i><font color='C3510A'>caractere</font></i>");
			corrigido = corrigido.replaceAll("inteiro", "<i><font color='C3510A'>inteiro</font></i>");
			corrigido = corrigido.replaceAll("logico", "<i><font color='C3510A'>logico</font></i>");
			
			corrigido = corrigido.replaceAll("Variaveis", "<b>Variaveis</b>");
			corrigido = corrigido.replaceAll("Inicio", "<b>Inicio</b>");
			corrigido = corrigido.replaceAll("Fim", "<b>Fim</b>");
		}
		corrigido = corrigido.replace("\t", "&emsp;");
		corrigido = corrigido.replace(" ", "&nbsp;");
		corrigido = corrigido.replaceAll("\n", "<br/>");			
		
		html+=corrigido+"</body></html>";
		
		return html;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	public String[] corrigirParaJava(String text, String nomeArquivo) {
		String [] retorno = new String[3];
		
		String html = 	"<html>"
				+ 			"<head>"
				+ 				"<title></title>"
				+ 				"<style>"
				+ 					"body{font-family: 'Courier New'; color:'000000'; font-size:10px}"
				+ 				"</style>"
				+ 			"</head>"
				+ 			"<body>";
		
		String java =	"import java.util.Scanner;\nimport java.io.IOException;\n"
					+ "import java.io.File;\n"
					+ "import java.io.FileWriter;\n\n"
					+	"public class "+nomeArquivo+" {\n"
					+		 "\tpublic static void main (String[] args) throws IOException {\n"
					+ 			"\t\tScanner in = new Scanner(System.in);\n"
					+ 			"\t\tFile file = new File(\"listaDeComandos.txt\");\n"
					+ 			"\t\tFileWriter fw = new FileWriter(file);\n";
		
		
		String linhasHtml[] = text.split("\n"), txtHtml="";
		String linhasJava[] = text.split("\n"), txtJava="";
		boolean Inicio= false, Fim = false, Variaveis= false, Varstxt=false;
		String booleanos="", doubles="", inteiros="", strings="", chars ="", tabs=""; 
		
		
		for (int i = 0; i < linhasHtml.length; i++) {
			boolean se = false;
			
			if(linhasHtml[i].replaceAll(" ", "").equalsIgnoreCase("Variaveis") && !Variaveis){
				linhasJava[i] = "";
				linhasHtml[i] = linhasHtml[i].replaceAll("Variaveis", "<b>Variaveis</b>");
				Variaveis=true;
			
			}else if(linhasHtml[i].replaceAll(" ", "").equalsIgnoreCase("Inicio") && !Inicio){
				linhasJava[i] = "";
				linhasHtml[i] = linhasHtml[i].replaceAll("Inicio", "<b>Inicio</b>");
				Inicio=true;
			
			}else if(linhasHtml[i].replaceAll(" ", "").equalsIgnoreCase("Fim") && !Fim){
				linhasJava[i] = "";
				linhasHtml[i] = linhasHtml[i].replaceAll("Fim", "<b>Fim</b>");
				Fim=true;
			
			}else{
				if(Variaveis && !Inicio){
					String var[] = linhasHtml[i].split(":");
					
					if(var.length == 2 && !var[0].replaceAll("\t", "").replaceAll(" ", "").equals("") && !var[1].replaceAll("\t", "").replaceAll(" ", "").equals("")){
						var[1] = var[1].replaceAll("\t", "").replaceAll(" ", "");
						String tipo="";
						
						if(var[1].equalsIgnoreCase("real")){
							tipo = "double";
							var[1] = var[1].replaceAll("real", "<i><font color='C3510A'>real</font></i>");
							doubles += var[0].replaceAll(" ", "").replaceAll("\t", "");
							
						}else if(var[1].equalsIgnoreCase("inteiro")){
							tipo = "int";
							var[1] = var[1].replaceAll("inteiro", "<i><font color='C3510A'>inteiro</font></i>");
							inteiros += var[0].replaceAll(" ", "").replaceAll("\t", "");
							
						}else if(var[1].equalsIgnoreCase("caractere")){
							tipo = "char";
							var[1] = var[1].replaceAll("caractere", "<i><font color='C3510A'>caractere</font></i>");
							chars += var[0].replaceAll(" ", "").replaceAll("\t", "");
							
						}else if(var[1].equalsIgnoreCase("texto")){
							tipo = "String";
							var[1] = var[1].replaceAll("texto", "<i><font color='C3510A'>texto</font></i>");
							strings += var[0].replaceAll(" ", "").replaceAll("\t", "");
							
						}else if(var[1].equalsIgnoreCase("logico")){
							tipo = "boolean";
							var[1] = var[1].replaceAll("logico", "<i><font color='C3510A'>logico</font></i>");
							booleanos += var[0].replaceAll(" ", "").replaceAll("\t", "");
						}
						linhasJava[i] = tipo+" "+var[0].replaceAll(" ", "")+";";
						linhasHtml[i] = var[0].replaceAll(" ", "")+" : "+var[1];
					}else{
						linhasJava[i] = "";
					}
				}else if(Variaveis && Inicio && !Fim){
					if(Varstxt == false){
						java = java + "\nfw.append(\""+doubles+"\\n\");"
								+ 		"\nfw.append(\""+inteiros+"\\n\");"
								+ 		"\nfw.append(\""+strings+"\\n\");\n";
						Varstxt = true;
					}
					if(linhasHtml[i].contains("(") && linhasHtml[i].contains("<-")){
						int primeiroP = linhasHtml[i].indexOf('(');
						int primeiroI = linhasHtml[i].indexOf("<-");
						
						if(primeiroI < primeiroP){
							linhasJava[i] = linhasJava[i]+";";
							linhasJava[i] = linhasJava[i].replaceAll("<-", "=");
							linhasHtml[i] = linhasHtml[i].replaceFirst("<-", "&lt;-");
						
						}else{
							String comando = linhasHtml[i].substring(0, primeiroP).replaceAll("\t", "").replaceAll(" ", "");
							
							if(comando.equalsIgnoreCase("escreva")){
								linhasHtml[i] = linhasHtml[i].replaceFirst("escreva", "<b><font color='781D9F'>escreva</font></b>");
								linhasJava[i] = linhasJava[i].replaceFirst("escreva", "System.out.println")+";";
							
							}else if(comando.equalsIgnoreCase("se")){
								String aux = linhasJava[i].trim().replaceAll("se", "").replaceAll("então", "").trim();
								
								linhasJava[i] = linhasJava[i].replaceFirst("se", "if( ");
								linhasJava[i] = linhasJava[i].replaceFirst("então", " ){ ");
								linhasJava[i] = linhasJava[i].replace(" e ", "&&");
								linhasJava[i] = linhasJava[i].replace(" ou ", "||");
								linhasJava[i] = linhasJava[i].replaceAll(" <> ", "!=");
								linhasJava[i] = linhasJava[i].replaceAll(" MOD ", "%");
								linhasJava[i] = linhasJava[i].replaceAll(" = ", " == ");
								linhasHtml[i] = linhasHtml[i].replaceAll("<", "&lt;");
								linhasHtml[i] = linhasHtml[i].replaceFirst("se", "<b><font color='0000ff'>se</font></b>");
								linhasHtml[i] = linhasHtml[i].replaceFirst("então", "<b><font color='0000ff'>então</font></b>");
								linhasHtml[i] = linhasHtml[i].replaceAll("<=", "&lt;=");
								linhasHtml[i] = linhasHtml[i].replaceAll(">=", "&gt;=");
								
								linhasJava[i] = linhasHtml[i]+"fw.append("+aux+")";
								
								tabs += "\t";
								se = true;
							}else if(comando.equalsIgnoreCase("senão_se")){
								String aux = linhasJava[i].trim().replaceAll("senão_se", "").replaceAll("então", "").trim();
								
								linhasJava[i] = linhasJava[i].replaceFirst("senão_se", "}else if( ");
								linhasJava[i] = linhasJava[i].replaceFirst("então", " ){ ");
								linhasJava[i] = linhasJava[i].replaceAll(" e ", "&&");
								linhasJava[i] = linhasJava[i].replaceAll(" <> ", "!=");
								linhasJava[i] = linhasJava[i].replaceAll(" MOD ", "%");
								linhasJava[i] = linhasJava[i].replace(" ou ", "||");
								linhasJava[i] = linhasJava[i].replaceAll(" = ", " == ");
								linhasHtml[i] = linhasHtml[i].replaceAll("<", "&lt;");
								linhasHtml[i] = linhasHtml[i].replaceFirst("senão_se", "<b><font color='0000ff'>senão_se</font></b>");
								linhasHtml[i] = linhasHtml[i].replaceFirst("então", "<b><font color='0000ff'>então</font></b>");
								linhasHtml[i] = linhasHtml[i].replaceAll("<=", "&lt;=");
								linhasHtml[i] = linhasHtml[i].replaceAll(">=", "&gt;=");
								
								linhasJava[i] = linhasHtml[i]+"fw.append("+aux+");";
								
								se = true;
							}else{
								linhasJava[i] = "";
							}
						}
					}else if(linhasHtml[i].contains("(")){
						String comando = linhasHtml[i].substring(0, linhasHtml[i].indexOf("(")).replaceAll("\t", "").replaceAll(" ", "");
						if(comando.equalsIgnoreCase("escreva")){
							String aux = linhasJava[i].replaceFirst("escreva", "");
									
							linhasHtml[i] = linhasHtml[i].replaceFirst("escreva", "<b><font color='781D9F'>escreva</font></b>");
							linhasJava[i] = linhasJava[i].replaceFirst("escreva", "System.out.println")+";\nfw.append("+aux+"+\"\\n\");";
						
						}else if(comando.equalsIgnoreCase("se")){
							String aux = linhasJava[i].trim().replaceAll("se", "").replaceAll("então", "").trim();
							
							linhasJava[i] = linhasJava[i].replaceFirst("se", "\t\tif");
							linhasJava[i] = linhasJava[i].replaceFirst("então", "{ ");
							linhasJava[i] = linhasJava[i].replaceAll(" e ", "&&");
							linhasJava[i] = linhasJava[i].replaceAll(" MOD ", "%");
							linhasJava[i] = linhasJava[i].replaceAll(" <> ", "!=");
							linhasJava[i] = linhasJava[i].replace(" ou ", "||");
							linhasJava[i] = linhasJava[i].replaceAll(" = ", " == ");
							linhasHtml[i] = linhasHtml[i].replaceAll("<", "&lt;");
							linhasHtml[i] = linhasHtml[i].replaceFirst("se", "<b><font color='0000ff'>se</font></b>");
							linhasHtml[i] = linhasHtml[i].replaceFirst("então", "<b><font color='0000ff'>então</font></b>");
							linhasHtml[i] = linhasHtml[i].replaceAll("<=", "&lt;=");
							linhasHtml[i] = linhasHtml[i].replaceAll(">=", "&gt;=");
							
							linhasJava[i] = "fw.append(\""+aux+"\");\n"+linhasJava[i]+"\nfw.append(\" # sim\");";
							
							tabs += "\t";
							se =true;
						}else if(comando.equalsIgnoreCase("senão_se")){
							String aux = linhasJava[i].trim().replaceAll("senão_se", "").replaceAll("então", "").trim();
							
							linhasJava[i] = linhasJava[i].replaceFirst("senão_se", "}else if");
							linhasJava[i] = linhasJava[i].replaceFirst("então", "{ ");
							linhasJava[i] = linhasJava[i].replace(" e ", "&&");
							linhasJava[i] = linhasJava[i].replaceAll(" MOD ", "%");
							linhasJava[i] = linhasJava[i].replace(" ou ", "||");
							linhasJava[i] = linhasJava[i].replaceAll(" <> ", "!=");
							linhasJava[i] = linhasJava[i].replaceAll(" = ", " == ");
							linhasHtml[i] = linhasHtml[i].replaceAll("<", "&lt;");
							linhasHtml[i] = linhasHtml[i].replaceFirst("senão_se", "<b><font color='0000ff'>senão_se</font></b>");
							linhasHtml[i] = linhasHtml[i].replaceFirst("então", "<b><font color='0000ff'>então</font></b>");
							linhasHtml[i] = linhasHtml[i].replaceAll("<=", "&lt;=");
							linhasHtml[i] = linhasHtml[i].replaceAll(">=", "&gt;=");
							
							linhasJava[i] = "fw.append("+aux+")"+linhasJava[i];
							
							se = true;
						}else if(comando.equalsIgnoreCase("leia")){
							linhasHtml[i] = linhasHtml[i].replaceFirst("leia", "<b><font color='1BBC26'>leia</font></b>");
							
							if(linhasJava[i].lastIndexOf(")") != 0){
								String aux = linhasJava[i].substring(linhasJava[i].indexOf("(")+1, linhasJava[i].lastIndexOf(")"));
								
								if(inteiros.contains(aux)){
									linhasJava[i] = aux+" = in.nextInt();";
									
								}else if(doubles.contains(linhasJava[i].substring(linhasJava[i].indexOf("(")+1, linhasJava[i].lastIndexOf(")")))){
									linhasJava[i] = aux+" = in.nextDouble();";
									
								}else if(strings.contains(linhasJava[i].substring(linhasJava[i].indexOf("(")+1, linhasJava[i].lastIndexOf(")")))){
									linhasJava[i] = aux+" = in.next();";
									
								}else if(chars.contains(linhasJava[i].substring(linhasJava[i].indexOf("(")+1, linhasJava[i].lastIndexOf(")")))){
									linhasJava[i] = aux+" = in.next().chatAt(0);";
									
								}else{
									linhasJava[i] = "";
								}
								
								linhasJava[i] = linhasJava[i]+"\nfw.append(\""+aux+" = \"+ "+aux+"+\"\\n\");";
								
							}else{
								linhasJava[i] = "";
							}
							
						}else if(comando.equalsIgnoreCase("enquanto")){
							
							linhasJava[i] = linhasJava[i].replace(" e ", "&&");
							linhasJava[i] = linhasJava[i].replaceAll(" MOD ", "%");
							linhasJava[i] = linhasJava[i].replace(" ou ", "||");
							linhasJava[i] = linhasJava[i].replaceAll(" <> ", "!=");
							linhasJava[i] = linhasJava[i].replaceAll(" = ", " == ");
							linhasHtml[i] = linhasHtml[i].replaceAll("<", "&lt;");
							linhasHtml[i] = linhasHtml[i].replaceAll("<=", "&lt;=");
							linhasHtml[i] = linhasHtml[i].replaceAll(">=", "&gt;=");
							linhasHtml[i] = linhasHtml[i].replaceFirst("enquanto", "<b><font color='b20505'>enquanto</font></b>");
							linhasHtml[i] = linhasHtml[i].replaceFirst("faça", "<b><font color='b20505'>faça</font></b>");
							
							if(!linhasJava[i].replaceAll(" ", "").contains(")faça")){
								linhasJava[i] = linhasJava[i].replace("enquanto", "}while")+";";
								tabs = tabs.replaceFirst("\t", "");
								se = false;
							}else{
								linhasJava[i] = linhasJava[i].replaceFirst("faça", "{");
								linhasJava[i] = linhasJava[i].replace("enquanto", "while");					
								tabs += "\t";
								se = true;
							}
							
						}else if(comando.equalsIgnoreCase("para")){
							String aux = linhasJava[i].replaceAll("para", "").replaceAll("faça", "");
							
							linhasJava[i] = linhasJava[i].replaceFirst("para", "for");
							linhasJava[i] = linhasJava[i].replaceFirst("faça", "{");
							linhasJava[i] = linhasJava[i].replace(" e ", "&&");
							linhasJava[i] = linhasJava[i].replaceAll(" MOD ", "%");
							linhasJava[i] = linhasJava[i].replace(" ou ", "||");
							linhasJava[i] = linhasJava[i].replaceAll(" <> ", "!=");
							linhasJava[i] = linhasJava[i].replaceAll(" = ", " == ");
							linhasHtml[i] = linhasHtml[i].replaceAll("<", "&lt;");
							linhasHtml[i] = linhasHtml[i].replaceAll("<=", "&lt;=");
							linhasHtml[i] = linhasHtml[i].replaceAll(">=", "&gt;=");
							linhasHtml[i] = linhasHtml[i].replaceFirst("para", "<b><font color='b20505'>para</font></b>");
							linhasHtml[i] = linhasHtml[i].replaceFirst("faça", "<b><font color='b20505'>faça</font></b>");
							tabs += "\t";
							se = true;
						
							linhasJava[i] = linhasJava[i]+"\nfw.append(\""+aux.split(";")[1]+"\\n\");";
							linhasJava[i] = linhasJava[i]+"\nfw.append(\"\");";
						}else{
							linhasJava[i] = "";
						}
						
					}else if(linhasHtml[i].contains("<-")){
						String aux = linhasJava[i].trim().split("<-")[0].trim();

						linhasHtml[i] = linhasHtml[i].replaceAll("<-", "&lt;-");
						linhasJava[i] = linhasJava[i].replaceAll("<-", "=");
						linhasJava[i] = linhasJava[i].replaceAll(" MOD ", "%");
						linhasJava[i] = linhasJava[i].replaceAll("Novo", "new");
						linhasJava[i] = linhasJava[i].replaceAll("Inteiro", "int");
						linhasJava[i] = linhasJava[i]+";";
						
						linhasJava[i] = linhasJava[i]+"\nfw.append(\""+aux+" = \"+ "+aux+"+\"\\n\");";
						
					}else if(linhasHtml[i].contains("fim_se")){
						linhasJava[i] = linhasJava[i].replaceFirst("fim_se", "}");
						linhasHtml[i] = linhasHtml[i].replaceFirst("fim_se", "<b><font color='0000ff'>fim_se</font></b>");
						tabs = tabs.replaceFirst("\t", "");
					
					}else if(linhasHtml[i].contains("fim_para")){
						linhasJava[i] = linhasJava[i].replaceFirst("fim_para", "}");
						linhasHtml[i] = linhasHtml[i].replaceFirst("fim_para", "<b><font color='b20505'>fim_para</font></b>");
						tabs = tabs.replaceFirst("\t", "");
						
					}else if(linhasHtml[i].contains("fim_enquanto")){
						linhasJava[i] = linhasJava[i].replaceFirst("fim_enquanto", "}");
						linhasHtml[i] = linhasHtml[i].replaceFirst("fim_enquanto", "<b><font color='b20505'>fim_enquanto</font></b>");
						tabs = tabs.replaceFirst("\t", "");
						
					}else if(linhasHtml[i].contains("senão")){
						linhasJava[i] = linhasJava[i].replaceFirst("senão", "}else{")+"\nfw.append(\" # nao\");";
						linhasHtml[i] = linhasHtml[i].replaceFirst("senão", "<b><font color='0000ff'>senão</font></b>");
						se =true;
					
					}else if(linhasHtml[i].contains("faça")){
						linhasJava[i] = linhasJava[i].replaceFirst("faça", "do{");
						linhasHtml[i] = linhasHtml[i].replaceFirst("faça", "<b><font color='b20505'>faça</font></b>");
						tabs += "\t";
						se = true;
						
					}else{
						linhasJava[i] = linhasJava[i].replaceAll(" MOD ", "%");
						linhasJava[i]="";						
					}
				}else if(Fim){
					linhasJava[i]="";
				}
			}
			
			txtHtml += linhasHtml[i]+"\n";
			if(se)
				txtJava += "\t"+tabs+linhasJava[i].replaceAll("\t", "")+"\n";
			else
				txtJava += "\t\t"+tabs+linhasJava[i].replaceAll("\t", "")+"\n";
		}
		txtHtml = txtHtml.replace("\t", "&emsp;");
		txtHtml = txtHtml.replace(" ", "&nbsp;");
		txtHtml = txtHtml.replaceAll("\n", "<br/>");
		
		html += txtHtml+"</body></html>";
		java += txtJava;
		
		retorno[2] ="";
		for (String linha : java.split("\n")) {
			if(!linha.contains("fw.append(") && !linha.contains("file")){
				if(linha.contains("if(")){
					linha = "\t\t"+linha;
				}
				retorno[2] = retorno[2]+linha+"\n";
			}
		}
		
		retorno[0] = html;
		retorno[1]= java+"\nfw.append(\"fim\\n\");";
		
		return retorno;
	}
}
