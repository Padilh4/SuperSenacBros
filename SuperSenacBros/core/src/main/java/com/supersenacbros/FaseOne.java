package com.supersenacbros;

import java.security.Timestamp;
import java.time.temporal.Temporal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;


public class FaseOne implements Screen {
	private SuperSenacBros game;
	
	public FaseOne(SuperSenacBros game) {
	    this.game = game;
	}
	

	
SpriteBatch batch;
Texture tChar, tChar2, questionB, questionBU, sky, brick_blocks, w1, w2, w3, w4,C1,C2,C3,C4,e1,e2, tmushroom, tcharp, tCharm, tCharHead, superchar, tCharBan;
private Sprite Char;
float vposx, vposy, velocityY = 0, deadVelocityY= 0, jumptimer = 0, contadorTempo = 0f, timecoinUI;;
final float velocity = 4f, gravity = -0.75f, jump_strength = 6.5f, jump_hold_strength = 0.74f, jumptimemax = 0.28f;
boolean inFloor = false, isDead = false, GameOver = false, isBig = false;
TiledMap map;
OrthographicCamera camera, uiCamera;
OrthogonalTiledMapRenderer mapRenderer;
private Rectangle CharBox;
private Array<Block> platforms;
Animation<TextureRegion> walkAnimation;
private Animation<Texture> coinAnimation, enemyAnimation;
private float stateTime = 0f, stateTimecoin = 0f;
private Array<Enemy1> EnemiesList;
Array<Texture> coinFrames;
private Array<FloatingText> floatingTexts = new Array<>();

int timePhase;

String namePhase = "1-1";
BitmapFont font, fontScore;
private Array<BouncingCoin> bouncingCoins = new Array<>();
private Array<Mushroom> MushroomList = new Array<>();
float invincibleTimer = 0f;
float DeadTimer = 2.5f;
boolean SavedinDB = false;
static String PlayerName = "";
static String CharacterName = "LEO";
boolean isWinning = false;
boolean touchingFloorCutscene = false;
float targetCastleX = 0f; // Posição da porta do castelo
int typesOfCutscene = 1;    // 1  Deslizar 2 Andar pro castelo 3 Sumir e salva
float cutsceneFloorY = 0f;
boolean collideFlag = false;
private float timeCountTimer = 0f;
private final float TIME_COUNT_DELAY = 0.01f;
private Sound coinSound;      
private Sound jumpSound;      
private Sound DeathSound;     
private Sound SelectSound;     
private Music EndMusic;      
private Sound KillEnemy;     
private Sound LossMush;   
private Sound DrinkCoffe;
private Sound LifeIncremental;
boolean CONFIRM = false;

private boolean ShowPass = false;

private Music backgroundMusic; //  música de fundo da fase
private boolean flipX = false;

boolean walkRight;
boolean walkLeft;
boolean PressedJump;

//======================================================================================================================
//                                       METODO CREATE (INICIO)
//======================================================================================================================

@Override
public void show() {
	
	//=============================================================== EVITAR BUGS NA MORTE ========================================================
	if (MushroomList != null) {
		MushroomList.clear();
	}
	if (bouncingCoins != null) {
		bouncingCoins.clear();
	}
	if (floatingTexts != null) {
		floatingTexts.clear();
	}
	//=============================================================================================================================================================
	
	//=============================================================== INICIALIZAÇÃO DE SISTEMAS DA ENGINE ========================================================
	batch = new SpriteBatch();

	camera = new OrthographicCamera();
	camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //configurar a camera

	map = new TmxMapLoader().load("Mapateste.tmx"); // puxar o mapa do assets
	mapRenderer = new OrthogonalTiledMapRenderer(map, 1f); // configurar a renderização, 1f padrao, 2f maior.

	int tileHeight = map.getProperties().get("tileheight", Integer.class);//altura dos blocos do mapa (32x32)
	int mapHeightInTiles = map.getProperties().get("height", Integer.class);//altura do mapa em blocos (15)

	timePhase = 300;
	DeadTimer = 2.5f;
    PlayerName = RegisterScreen.NamePlayer;
    CharacterName = SelectScreen.NameCharacter;
    
    
	//=============================================================================================================================================================

	//=============================================================== CARREGAMENTO DE TEXTURAS (ASSETS) ==========================================================

	C1 = new Texture("Coin_1.png");
	C2 = new Texture("Coin_2.png");
	C3 = new Texture("Coin_3.png");
	C4 = new Texture("Coin_4.png");
	e1 = new Texture("enemy_1_1.png");
	e2 = new Texture("enemy_1_2.png");
	tmushroom = new Texture("Mushroom.png");
	
	brick_blocks = new Texture("blocos.png");
	questionB = new Texture("question_block.png");
	questionBU = new Texture("question_block_used.png");
	sky = new Texture("Ceu.png");
	//=============================================================================================================================================================

//============================= CARREGAMENTO SIMPLES DE TEXTURAS BASEADO NO PERSONAGEM ============================================================================

if (CharacterName.equals("LEO")) {
tcharp = new Texture("leo_pulando.png");       // Textura parado / pulando
tChar = new Texture("leo_parado.png");      
tCharm = new Texture("leomorto.png");
tCharHead = new Texture("leo_head.png");// Cabeça para a UI
tCharBan = new Texture("leo_mastro.png");

// array com as 3 imagens de andar do Leo
TextureRegion[] walkFrames = new TextureRegion[3];
walkFrames[0] = new TextureRegion(new Texture("leo_andando1.png"));
walkFrames[1] = new TextureRegion(new Texture("leo_andando2.png"));
walkFrames[2] = new TextureRegion(new Texture("leo_andando3.png"));

// Passa o array de imagens separadas para a animação
walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);

} else if (CharacterName.equals("LUIS")) {
	tcharp = new Texture("luis_pulando.png");       // Textura parado / pulando
	tChar = new Texture("luis_parado.png");      
	tCharm = new Texture("luismorto.png");
	tCharHead = new Texture("luis_head.png"); 
	
	tCharBan = new Texture("luis_mastro.png");
// array com as 3 imagens  de andar do Luis
TextureRegion[] walkFrames = new TextureRegion[4];
walkFrames[0] = new TextureRegion(new Texture("luis_andando1.png"));
walkFrames[1] = new TextureRegion(new Texture("luis_andando2.png"));
walkFrames[2] = new TextureRegion(new Texture("luis_andando3.png"));
walkFrames[3] = new TextureRegion(new Texture("luis_andando4.png"));
//Passa o array de imagens separadas para a animação
walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);

} else {

	tcharp = new Texture("vini_pulando.png");       // Textura parado / pulando
	tChar = new Texture("vini_parado.png");      
	tCharm = new Texture("vinimorto.png");
	tCharHead = new Texture("vini_head.png"); 
	
	tCharBan = new Texture("vini_mastro.png");
//array com as 4 imagens de andar do Vini
TextureRegion[] walkFrames = new TextureRegion[4];
walkFrames[0] = new TextureRegion(new Texture("vini_andando1.png"));
walkFrames[1] = new TextureRegion(new Texture("vini_andando2.png"));
walkFrames[2] = new TextureRegion(new Texture("vini_andando3.png"));
walkFrames[3] = new TextureRegion(new Texture("vini_andando4.png"));
//Passa o array de imagens separadas para a animação
walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
}
//Inicializa o sprite com a textura padrão que foi escolhida acima
Char = new Sprite(tChar);
Char.setSize(24, 40); // Começa pequeno

	//=============================================================== MONTAGEM DAS ANIMAÇÕES (Coin, enemy e walk)=====================================================================
	

	coinFrames = new Array<>();//lista de sprites do coin
	coinFrames.add(C1);
	coinFrames.add(C2);
	coinFrames.add(C3);
	coinFrames.add(C4);
	
	Array<Texture> enemyFrames = new Array<>();
	enemyFrames.add(e1);
	enemyFrames.add(e2);
	
	coinAnimation = new Animation<>(0.2f, coinFrames);
	
	enemyAnimation = new Animation<>(0.2f, enemyFrames);
	coinAnimation.setPlayMode(Animation.PlayMode.LOOP);
	walkAnimation.setPlayMode(Animation.PlayMode.LOOP);//repete as animações de andar em loop
	enemyAnimation.setPlayMode(Animation.PlayMode.LOOP);
	//=============================================================================================================================================================
	
	//=============================================================== CARREGAMENTO SONS ==========================================================================
		// efeitos sonoros
	    coinSound = Gdx.audio.newSound(Gdx.files.internal("Coin.wav"));
	    jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
	    DeathSound = Gdx.audio.newSound(Gdx.files.internal("Death.wav"));
	    SelectSound = Gdx.audio.newSound(Gdx.files.internal("SelectMenu.wav"));
	    KillEnemy = Gdx.audio.newSound(Gdx.files.internal("KillEnemy.wav"));
	    LossMush = Gdx.audio.newSound(Gdx.files.internal("LossMush.wav"));
	    DrinkCoffe = Gdx.audio.newSound(Gdx.files.internal("drinkcoffee.wav"));
	    LifeIncremental = Gdx.audio.newSound(Gdx.files.internal("LifeIncremental.wav"));
	    

	    // musica de fundo
	    backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("BackgroundMusic.mp3"));

	    // Configurações da música de fundo
	    backgroundMusic.setLooping(true); // Faz a musica recomeçar quando acabar
	    backgroundMusic.setVolume(0.5f);  // mudar volume
	    
	    backgroundMusic.play();           // toca musica quando começa
		
	    EndMusic = Gdx.audio.newMusic(Gdx.files.internal("Endmusic.mp3"));
		//=============================================================================================================================================================

	//=============================================================== CONFIGURAÇÃO INICIAL DO JOGADOR ============================================================
		
		

		vposx = 20;
		vposy = 64.5f;
		CharBox = new Rectangle(vposx + 4, vposy, 16, 32);
		isBig = false; // Garante que começa falso ao resetar a fase
		isWinning = false;
	    
	    

		//=============================================================================================================================================================

	//=============================================================== CARREGAMENTO DE BLOCOS SÓLIDOS DO MAPA =====================================================
	platforms = new Array<>();
	MapLayer layersolids = map.getLayers().get("solids");//pega o conjunto solids do tiled

	if(layersolids != null) {//verifica se existe algum solids
	for(MapObject object : layersolids.getObjects()) {//cada solid vira um object
	if (object instanceof RectangleMapObject) {//se esse object for um rectangle
	Rectangle rectangleTiled = ((RectangleMapObject) object).getRectangle();//pega as proporções do retangulo
	String blockName = object.getName();//pega o nome desse retangulo
	if("brick_block".equals(blockName)) {//se for um question_block cria um bloco com o seu nome e textura
	platforms.add(new Block(rectangleTiled, blockName, brick_blocks, 1));
	} else if ("question_block_coin".equals(blockName)){//se for um brick_block_coin cria um bloco com o seu nome e textura e moeda
		Block b = new Block(rectangleTiled, blockName, questionB, 1);
		b.conteudo = "coin";
		platforms.add(b);
	}else if("question_block_mush".equals(blockName)){//se for um brick_block_coin cria um bloco com o seu nome e textura e cogumelo
		Block b = new Block(rectangleTiled, blockName, questionB, 1);
		b.conteudo = "mushroom";
		platforms.add(b);
		
	}
	
	
	else if("Coin".equals(blockName)){
	Rectangle hitboxMoeda = new Rectangle(rectangleTiled);

	float novaLargura = 16;
	float novaAltura = 20;

	hitboxMoeda.x += (hitboxMoeda.width - novaLargura) / 2f;
	hitboxMoeda.y += (hitboxMoeda.height - novaAltura) / 2f;
	hitboxMoeda.width = novaLargura;
	hitboxMoeda.height = novaAltura;

	platforms.add(new Block(hitboxMoeda, blockName, coinAnimation.getKeyFrame(stateTimecoin), 1));
	}else {
	//se n for nada disso n precisa de textura
	platforms.add(new Block(rectangleTiled, blockName, null, 1));
	}
	}
	}
	}
	//=============================================================================================================================================================

	//=============================================================== CARREGAMENTO E SPAWN DOS INIMIGOS ===========================================================
	EnemiesList = new Array<>();
	MapLayer layerEnemies = map.getLayers().get("enemies"); // Pega a camada enemies

	if (layerEnemies != null) {
	for (MapObject object : layerEnemies.getObjects()) {
	if (object instanceof RectangleMapObject) {
	Rectangle rectTiled = ((RectangleMapObject) object).getRectangle();
	String enemyName = object.getName();

	if ("malware".equals(enemyName)) {
	// hitbox do inimigo igual ao retangulo criado(proproções etc)
	Rectangle hitboxInimigo = new Rectangle(rectTiled);

	// Instancia o inimigo passando a hitbox e a animação dele

	EnemiesList.add(new Enemy1(hitboxInimigo, enemyAnimation));
	}
	}
	}
	}
	//=============================================================================================================================================================

	// ===================================== CRIACAO DA FONTE ===========================================================
	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMarioWorldTextBoxRegular-Y86j.ttf"));

	// configurações da fonte principal (tamanho e cor)
	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	parameter.size = 18; // tamanho
	parameter.color = Color.WHITE; // cor
	font = generator.generateFont(parameter); // Gera a fonte com os paramentros e guarda na variável 'font'

	
    parameter.size = 11;//tamaho da fonte score
    fontScore = generator.generateFont(parameter);
    

	generator.dispose();// descarta para nao pesar a memoria

	uiCamera = new OrthographicCamera();
	uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	//=======================================================================================================================
}

//======================================================================================================================
//                                           FIM DO METODO CREATE
//======================================================================================================================



//======================================================================================================================
//                                           METODO RENDER (GAME LOOP)
//======================================================================================================================
@Override
public void render(float delta) {
	if (GameOver && isDead && vposy < -100) {
		backgroundMusic.stop();
	    DrawGameOver();
	    if (!SavedinDB) {
	        ManageDB.SaveScore(PlayerName, game.score);
	        SavedinDB = true;
	    }
	    return; 
	}

	if (isDead && vposy < -100) {
		backgroundMusic.stop();
	    DrawDeath();
	    return; 
	}
	
	if(ShowPass) {
		DrawPass();
	}
	
	//================================================================= LOGICA DE MOVIMENTACAO ==============================================================
	
    walkRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
	 walkLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
	 PressedJump = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.SPACE);

	// Verifica se tem controle conectado
	if (Controllers.getControllers().size > 0) {
	    Controller controle = Controllers.getControllers().first();
	    
	    
	    // Lendo o analogico
	    float eixoX = controle.getAxis(0); 
	    
	    if (eixoX > 0.25f) walkRight = true;
	    if (eixoX < -0.25f) walkLeft = true;
	    
	    //Lendo o botão de Pulo 
	    
	    if (controle.getButton(0)) {
	        PressedJump = true;
	    }
	    if(controle.getButton(1)) {
	    	 CONFIRM = true;
	    }
	}
    //================================================================================================================================================================
	//=============================================================== ATUALIZAÇÃO DOS CRONÔMETROS DE ANIMAÇÃO ====================================================
	if (!isDead && !isWinning) {
	if (walkRight || walkLeft) {// Se tiver andando, o cronômetro avança.
	stateTime += Gdx.graphics.getDeltaTime(); // incrementa os milesimos que passaram
	} else {// Se estiver parado, o tempo zera.
	stateTime = 0f; // Reseta a animação
	}
	}
	if (invincibleTimer > 0) {
        invincibleTimer -= Gdx.graphics.getDeltaTime();
    }
	stateTimecoin += Gdx.graphics.getDeltaTime();
	//=============================================================================================================================================================
	
	//=============================================================== MOVIMENTO NA CUTSCENE ======================================================================
	if (isWinning) {
	    
	    if (typesOfCutscene == 2) {// Avança o tempo da animação de andar se tiver caminhando
	        stateTime += Gdx.graphics.getDeltaTime();
	    }

	    switch (typesOfCutscene) {
	        case 1: // deslizar
	        	
	        	backgroundMusic.stop();
	        	EndMusic.setLooping(true);
	        	EndMusic.setVolume(0.5f);
	        	EndMusic.play();
	        	
	            Char.setTexture(tCharBan); // textura segurando a bandeira
	            vposy -= 2.5f; // desce do mastro 
	            
	            
	                if (vposy <= cutsceneFloorY) {// Checa se ele bateu no chão (bloco do chão)
	                    vposy = cutsceneFloorY; // fixa no chao
	                    typesOfCutscene = 2; // vai pra proxima parte da cutscene
	                    stateTime = 0f;
	                  
	                }
	            
	            break;

	        case 2: //caminhar
	            // troca a textura do personagem para animar a caminhada
	            TextureRegion frameAtual = walkAnimation.getKeyFrame(stateTime, true);
	            Char.setRegion(frameAtual);
	            
	            vposx += 1.5f; // personagem caminha pra direita
	            
	            // se ele chegou na porta do castelo, avança para a outra parte
	            if (vposx +12 >= targetCastleX) {
	            	vposy = -999;
	                typesOfCutscene = 3;
	            }
	            break;

	        case 3: // entrar no castelo e salvar
	        	timeCountTimer += delta;
	        	// Executa a cada intervalo de tempo
	            if (timeCountTimer >= TIME_COUNT_DELAY) {
	                timeCountTimer = 0f;

	                if (timePhase > 0) {
	                	timePhase--;          // Diminui 1 segundo 
	                	game.score += 50;     // Aumenta 50 pontos no score
	                    
	                     
	                     coinSound.play(0.07f); // Toca o som da moeda
	                }else {
	                    EndMusic.stop();
	                    namePhase = "FINAL";
	                    ShowPass = true;
	                    if(ShowPass) {
	                    	return;
	                    }
	                	ManageDB.SaveScore(PlayerName, game.score);
	                    game.setScreen(new FaseFinal(game)); 
	                    dispose();
	            return;
	                
	            }
	            
	            }
break;	           
	    }
	    
	    // atualiza a caixa de colisão dele durante a cutscene
	    CharBox.setPosition(vposx + 4, vposy);
	}
	//============================================================================================================================================================

	//=============================================================== FISICA E QUEDA EM BURACOS DO JOGADOR =======================================================
	if (!isDead && !isWinning) {
	this.moveChar();//movimento
	CharBox.setPosition(vposx + 4, vposy);//hitbox acompanhando o char quando as cordenadas mudarem
	if (CharBox.y < -50 && !isDead) { // 50 pixels de diferença para ele sumir um pouco no buraco antes de pular de susto
	    System.out.println("char caiu no buraco!");
	    game.lifes--;
	    if(game.lifes <= 0) {
	    	GameOver = true;
	    }
	    DeathSound.play();
	    isDead = true;
	    Char.setTexture(tCharm); // Muda para a textura de morto
	    deadVelocityY = 19f;     // Dá o pulinho de morte clássico do Mario
	    velocityY = 0;           // Zera a velocidade normal
	}
	// ===========================================================================================================================================================
	// ================================================================VERIFICADOR DE MORTE DO INIMIGO ===========================================================

	for (int i = EnemiesList.size - 1; i >= 0; i--) {
	    Enemy1 e = EnemiesList.get(i);
	    
	    if (e.iseDead) {
	    	
	        // Se o inimigo morre faz a física do pulo
	        e.deadEVelocityY += gravity;     // Aplica gravidade
	        e.hitbox.y += e.deadEVelocityY;  // Move o corpo
	        
	        // deleta do jogoquando sumir da tela
	        if (e.hitbox.y + e.hitbox.height < -50) {
	            EnemiesList.removeIndex(i);
	             continue;//pula pro proximo inimigo
	        }
	    } else {
	        // Se ele estiver vivo age normalmente
	        e.update(Gdx.graphics.getDeltaTime(), platforms, EnemiesList);
	        e.velocityY += gravity;
	        
	        if ((e.hitbox.y + e.hitbox.height) <= 0) {
	            EnemiesList.removeIndex(i);
	            continue;
	        }
	        
	 //=============================================================SISTEMA DE COLISÃO DO char COM O INIMIGO =========================================================
	        if (CharBox.overlaps(e.hitbox) && !isWinning) {
	            // Se o char estiver caindo , mata o inimigo
	            if (velocityY < 0) {
	                System.out.println("Matou o inimigo!");
	                KillEnemy.play(0.4f);
	                game.score += 100;
	                e.iseDead = true;
	                e.deadEVelocityY = 6f; // Força o pulo apos matar
	                e.velocityY = 0;       // Para o movimento padrão
	                
	                velocityY = 6.4f; // Impulso pro char saltar
	            } else {
	            	if (invincibleTimer > 0) {
                        System.out.println("char esta invencivel"); 
                    }
	            	else if (isBig) {
	            		
	            		
	                    // Se estiver grande nao morre
	                    System.out.println("char tomou dano mas nao morreu");
	                    isBig = false;
	                    LossMush.play();
	                    Char.setSize(24, 38);
	                    CharBox.setSize(16, 32);
	                    
	                    // Volta a textura
	                    Char.setTexture(tChar);
	                    
	                    invincibleTimer = 1.5f;
	            	} else {
	                    
	                    System.out.println("char morreu");
	                    game.lifes--;
	                    if (game.lifes <= 0) {
	                        GameOver = true;
	                    }
	                    DeathSound.play();
	                    isDead = true;
	                    Char.setTexture(tCharm);
	                    deadVelocityY = 12f; 
	                }
	            
	        
	            }
	        }
	    }
	}
	

	}
	
	
	//=============================================================================================================================================================
	//==============================================================SISTEMA DE COLISÃO DO char COM A BANDEIRA =======================================================
	for(Block b : platforms) {
		if (b.name != null && b.name.startsWith("win_") && !isWinning) {
		    if (CharBox.overlaps(b.bounds)) {
		        System.out.println("GANHOU, colidiu com a bandeira: " + b.name);
		        
		        // pega os pontos do nome do bloco (win_5000 vira 5000 pomto)
		        
		        if (b.name.equals("win_5000") &&  !collideFlag) {
		        	game.score += 5000;
		            collideFlag = true;
		            floatingTexts.add(new FloatingText("5000", b.bounds.x + 20,b.bounds.y + 10, 1.5f));
		        } else if (b.name.equals("win_2000") && !collideFlag) {
		        	game.score += 2000;
		            collideFlag = true;
		            floatingTexts.add(new FloatingText("2000", b.bounds.x + 20, b.bounds.y + 10, 1.5f));
		        } else if(b.name.equals("win_100")  && !collideFlag) {
		        	game.score += 100;
		        	collideFlag = true;
		        	floatingTexts.add(new FloatingText("100", b.bounds.x + 20, b.bounds.y + 10, 1.5f));
		        }

		        // ativa o boolean iswinning e deixa o personagem colado na bandeira
		        isWinning = true;
		        
		        if(CharacterName.equals("LUIS")) {
		        	vposx = b.bounds.x - 4; // centraliza no mastro
		        	if(isBig) {
		        		
		        		vposx = b.bounds.x - 5; // centraliza no mastro
		        		
		        	}
		        	
		        }else if(CharacterName.equals("char")) {
		        	vposx = b.bounds.x - 15;
		        	if(isBig) {
		        		
		        		vposx = b.bounds.x - 30; // centraliza no mastro
		        		
		        	}
		        	
		        }else {
		        	vposx = b.bounds.x -16;
		        	if(isBig) {
		        		
		        		vposx = b.bounds.x - 28; // centraliza no mastro
		        		
		        	}
		        	
		        }
		        
		        velocityY = 0;          // zera a velocidadeY normal
		        typesOfCutscene = 1;      // desliza
		        
		        // Define onde fica o castelo no mapa
		        targetCastleX = 6303; 
		        cutsceneFloorY = 64; // Valor padrão de segurança
				
			}
		}
	}
	//===============================================================================================================================================================
    //==============================================================SISTEMA DE COLISÃO DO char COM O COGUMELO =======================================================
	for (int i = MushroomList.size - 1; i >= 0; i--) {
		Mushroom m = MushroomList.get(i);
	if (CharBox.overlaps(m.hitbox)) {
       
		System.out.println("char pegou o cogumelo! Ficou GRANDE!");
		DrinkCoffe.play(0.3f);	
        isBig = true;
        game.score += 1000; // Pontuação por pegar o item
        
        // Muda a textura atual para o char maior
        
        Char.setSize(38, 60);
        CharBox.setSize(26, 60);
        floatingTexts.add(new FloatingText("1000", Char.getX() + Char.getWidth() / 2, Char.getY() + Char.getHeight() + 10, 1.5f));
        // Remove o cogumelo do jogo
        MushroomList.removeIndex(i);
       
            }
        
    
	}
	//===============================================================================================================================================================
	//=============================================================== COLISÕES LATERAIS DO JOGADOR COM MAPA =======================================================
	if (!isDead && !isWinning && !collideFlag) {
	if(velocityY > 0) {
	Char.setTexture(tcharp);
	}
	for(Block b : platforms) { //procura cada bloco do mapa
	if(b.name != null && b.name.equals("Coin")) { continue;}
	if(CharBox.overlaps(b.bounds)) { //testa se colidiu com um deles
		// se colidiu aqui, foi com certeza na lateral
	if (walkRight) {
	vposx = b.bounds.x - CharBox.width - 4;
	}
	else if (walkLeft) {
	vposx = b.bounds.x + b.bounds.width;
	}
	CharBox.setPosition(vposx + 4, vposy);
	}
	}
	} else if(isDead){
	    // Se o char morre, apenas aplica a gravidade pra ele cair
	    deadVelocityY += gravity; 
	    vposy += deadVelocityY; 
	
	}
	//=============================================================================================================================================================

	//=============================================================== POSICIONAMENTO DA CÂMERA DO JOGO ===========================================================
	if (!isDead) {
	    // Pega as propriedades do mapa
	    int tileWidth = map.getProperties().get("tilewidth", Integer.class); // 32
	    int mapWidthInTiles = map.getProperties().get("width", Integer.class); // qtd de blocos de largura
	    float MapMaxWidth = mapWidthInTiles * tileWidth; // largura total em pixels

	    // Define os limites da câmera 
	    float LeftMax = camera.viewportWidth / 2f;
	    float RightMax = MapMaxWidth - (camera.viewportWidth / 2f);

	   // trava a posicao X do char entre o limite da esquerda e da direita
	    float cameraX = com.badlogic.gdx.math.MathUtils.clamp(vposx, LeftMax, RightMax);

	    // Aplica a posicao travada na camera
	    camera.position.set(cameraX, camera.viewportHeight / 2f, 0);
	}

	ScreenUtils.clear(0.4f, 0.6f, 1f, 1f);
	camera.update(); // atualizador da camera conforme anda
	//=============================================================================================================================================================

	//=============================================================== DESENHO DA FASE E DOS SPRITES (MUNDO) =======================================================
	mapRenderer.setView(camera); //renderização acompanha a camera
	mapRenderer.render(); //começa a desenhar o mapa automaticamente

	batch.setProjectionMatrix(camera.combined); //batch siga a camera
	batch.begin();//ok eu vou seguir, iniciando...

	for (Block b : platforms) {
	if("Coin".equals(b.name)) {
	Texture frameAtual = coinAnimation.getKeyFrame(stateTimecoin);
	// Desenha o frame nas coordenadas originais reajustando os offsets visuais (+8 no X, +6 no Y)
	batch.draw(frameAtual, b.bounds.x - 16, b.bounds.y - 12, 32, 32);
	} else {
	b.draw(batch); // outros blocos continuam normais
	}
	}

	for (Enemy1 e : EnemiesList) {
	e.draw(batch);
	}
	
	// ================================================ LOGICA DOS COGUMELOS ===================================================================================
		for (int i = MushroomList.size - 1; i >= 0; i--) {
		    Mushroom m = MushroomList.get(i);
		    
		    
		    m.velocityY += gravity;
		    m.update(Gdx.graphics.getDeltaTime(), platforms, EnemiesList);
		    
		    //Se cair no buraco, remove da lista
		    if (m.hitbox.y < -50) {
		        MushroomList.removeIndex(i);
		        continue;
		    }
		    
		    //Desenha a textura do cogumelo seguindo a hitbox
		    batch.draw(m.texture, m.hitbox.x, m.hitbox.y, m.hitbox.width, m.hitbox.height);
		}
	// =========================================================================================================================================================
		
		// ====================================== EFEITO INVENCIVEL char =============================================
		if (invincibleTimer > 0 && ((int)(invincibleTimer * 10) % 2 == 0)) {
		    Char.setColor(1, 1, 1, 0.2f); // Fica bem transparente (efeito de piscar)
		} else {
		    Char.setColor(1, 1, 1, 1f);   // Opacidade normal
		}

		Char.setPosition(vposx, vposy); // necessário para inverter para y começar em cima
		Char.draw(batch);
		// =========================================================================================================================================================
		
	
	//=============================================================================================================================================================
	
	// ================================================ MOEDAS SALTADORAS DOS BLOCOS ===========================================================================
	for (int i = bouncingCoins.size - 1; i >= 0; i--) {
	    BouncingCoin bc = bouncingCoins.get(i);
	    bc.update(Gdx.graphics.getDeltaTime(), -0.35f); // passa a gravidade pro metodo
	    if (bc.shouldRemove) {
	        // Quando a moeda terminar o pulo o  jogador ganha os pontos
	    	
	        game.coins++;
	        if(game.coins == 20) {
	        	LifeIncremental.play(0.4f);
	        	game.lifes++;
	        	game.coins = 0;
	        }
	        game.score += 200;
	        bouncingCoins.removeIndex(i); 
	        coinSound.play();
	    	floatingTexts.add(new FloatingText("200", bc.x, bc.targetY + 10, 1.5f));
	    	
	    } else {
	        // Pega o frame da animação de moeda
	        Texture frameAtual = coinAnimation.getKeyFrame(stateTimecoin);
	        // Desenha a moeda rodando no ar
	        batch.draw(frameAtual, bc.x, bc.y, 16, 20);
	    }
	}
	// =========================================================================================================================================================
	
	// ================================================ TEXTOS FLUTUANTES (SCORE) ==============================================================================
	for (int i = floatingTexts.size - 1; i >= 0; i--) {
	    FloatingText ft = floatingTexts.get(i);
	    ft.update(Gdx.graphics.getDeltaTime());

	    if (ft.duration <= 0) {
	        floatingTexts.removeIndex(i); // Remove da lista quando o tempo acaba
	    } else {
	        fontScore.draw(batch, ft.text, ft.x, ft.y); // Desenha na tela
	    }
	}
	// =========================================================================================================================================================


	//============================================================== LÓGICA DO DESENHO DA UI =====================================================

	batch.setProjectionMatrix(uiCamera.combined); // o batch usa a câmera fixa da UI antes de desenhar os textos

	// Pega a largura e altura da tela para posicionar os textos 
	float screenWidth = Gdx.graphics.getWidth();
	float screenHeight = Gdx.graphics.getHeight();




	font.draw(batch, PlayerName + "\n" + String.format("%06d", game.score), 20, screenHeight - 20);// desenha o Score (Com zeros do lado "%06d")


	font.draw(batch, "WORLD\n  " + namePhase, screenWidth - 300, screenHeight - 20);// desenha o Nome da Fase


	font.draw(batch, "TIME\n " + timePhase, screenWidth - 120, screenHeight - 20);// Desenha o tempo



		if(!isDead && !isWinning) {
		timecoinUI += Gdx.graphics.getDeltaTime();
	}
		
		Texture CoinFrameUI = coinAnimation.getKeyFrame(timecoinUI);

		batch.draw(CoinFrameUI, screenWidth - 465, screenHeight - 58, 24, 24);

	font.draw(batch, "x" + String.format("%02d", game.coins), screenWidth - 440, screenHeight - 36); // desenha a moeda

	//==============================================================================================================================================


	


	//=============================================================== CÁLCULO DE PULO SEGURADO =========================================================
	if (!isDead && !isWinning) {

	if(PressedJump && jumptimer < jumptimemax && velocityY > 0) { // Se a seta pra cima continuar pressionada e o tempo de pulo não passou do limite
	jumptimer += Gdx.graphics.getDeltaTime(); // Acumula o tempo que o botão ta segurado
	velocityY += jump_hold_strength; //vai um pouco mais pra cima
	
	}else {

	if (velocityY > 0) {// Se soltou a setinha ou o tempo acabou ele cancela o impulso para ele começar a cair
	jumptimer = jumptimemax; // Força o fim do impulso
	}
	}

	velocityY += gravity; // A gravidade sempre age puxando o personagem para baixo
	vposy += velocityY; // Posição acompanha a velocidade
	CharBox.setPosition(vposx + 4, vposy);
	}
	//=============================================================================================================================================================

	//=============================================================== COLETA DE MOEDAS DO CHAO =========================================================
	for(int i = 0; i < platforms.size; i++) {
	Block b = platforms.get(i);
	if(b.name != null && b.name.equals("Coin")) {
	if(CharBox.overlaps(b.bounds)) {
	System.out.println("PLIM! +1 Moeda");
	game.coins++;
	if(game.coins == 20) {
    	LifeIncremental.play(0.4f);
    	game.lifes++;
    	game.coins = 0;
    }
	game.score += 200;
	platforms.removeIndex(i); // Remove a moeda do jogo
	i--;
	}
	}
	}
	//=============================================================================================================================================================

	//=============================================================== COLISÕES VERTICAIS (CHÃO E TETO/BLOCOS) ======================================================
	for(Block b : platforms) { //procura cada bloco do mapa
		if(isWinning) {
			break;
		}
		
	if(b.name != null && b.name.equals("Coin")) continue;

	if(CharBox.overlaps(b.bounds)) { //testa se colidiu com um deles
	float charfeet = CharBox.y;// Posição Y de baixo do char e de cima do bloco
	float blocoTeto = b.bounds.y + b.bounds.height;

	float charCabeca = CharBox.y + CharBox.height;// Posição Y de cima do char e de baixo do bloco
	float blocoFundo = b.bounds.y;

	if(velocityY <= 0) { // Caindo
	vposy = blocoTeto; // Fixa o boneco no topo do bloco
	velocityY = 0f; // Para a queda
	jumptimer = 0f;
	inFloor = true; // Está pisando em plataforma
	CharBox.setPosition(vposx + 4, vposy); // Atualiza hitbox
	break; // Sai do loop já que achou o chão

	} else if(velocityY > 0) { //se a parte de cima esta abaixo/ na linha do fundo do bloco (Subindo)
	vposy = blocoFundo - CharBox.height; // Fixa a parte de cima embaixo do bloco
	velocityY = 0f;
	CharBox.setPosition(vposx + 4, vposy);
	

	if(b.name != null && b.name.startsWith("question_block")) {//Cabeçada no senac_block 3 vezes para sair moedas e virar usado
	b.hits--;
	
	b.isBumping = true; 
    b.bumpTimer = 0;
	
	if(b.hits == 0) {

	b.texture = questionBU;
	b.name = "bloco_usado";
	
	if ("coin".equals(b.conteudo)) {
        System.out.println("PLIM (Moeda!)");
        // Faz nascer a moeda
        bouncingCoins.add(new BouncingCoin(b.bounds.x + 8, b.bounds.y + 32));
        
    } else if ("mushroom".equals(b.conteudo)) {
        System.out.println("TCHUIN (Cogumelo!)");
        Rectangle retangulo = new Rectangle();
        retangulo.setSize(32, 32);
        MushroomList.add(new Mushroom(b.bounds.x + 16,b.bounds.y + 32, retangulo, tmushroom));
    }
	break;
	}
	break;
	}

	if (b.name != null && b.name.equals("brick_block")) { //Cabeçada no tijolo para sair uma moeda de quebrar
	
	b.isBumping = true; 
    b.bumpTimer = 0;
    if(isBig) {
    	b.hits--;
    }
    if(b.hits == 0) {
    	platforms.removeValue(b, true);
    }
    
	break;
	}
	break; //nenhum desses entao só sai do loop
	}
	}
	}
	
	for (Block b : platforms) {
	    b.update(Gdx.graphics.getDeltaTime());
	}

	if(velocityY != 0) {// se cair de uma plataforma nao estara mais em um chao, portanto a gravidade vai agir
	inFloor= false;
	}
	//=============================================================================================================================================================
	
	// ================================================ LÓGICA DO TEMPO DA FASE ===============================================================================================
	if (!isDead && timePhase > 0 &&!isWinning) {
	    contadorTempo += Gdx.graphics.getDeltaTime();
	    if (contadorTempo >= 1f) { // Passou 1 segundo real
	    	timePhase--;
	        contadorTempo = 0f;
	    }
	}

	// Se o tempo acabar, o char morre
	if (!isDead && timePhase <= 0 && !isWinning) {
		game.lifes--;
	    isDead = true;
	    DeathSound.play();
	    Char.setTexture(tCharm);
	    deadVelocityY = 12f;
	}
	//===========================================================================================================================================================================
	
	batch.end();
}
//======================================================================================================================
//                                        FIM DO METODO RENDER
//======================================================================================================================



//======================================================================================================================
//                                          METODO DISPOSE (LIMPEZA)
//======================================================================================================================
@Override
public void dispose() {
batch.dispose();
mapRenderer.dispose();
tChar.dispose();

tCharHead.dispose();
font.dispose();
fontScore.dispose();
if (font != null) font.dispose();


if (coinSound != null) coinSound.dispose();
if (jumpSound != null) jumpSound.dispose();

if (backgroundMusic != null) {
    backgroundMusic.stop(); // Para a música
    backgroundMusic.dispose();
}

}

//======================================================================================================================
//                                        FIM DO METODO DISPOSE
//======================================================================================================================


//======================================================================================================================
//                                       METODO MOVEchar (MOVIMENTO)
//======================================================================================================================

private void moveChar() {
	if(isWinning) {//se estiver na cutscene teclado desativa
		return;
	}
boolean walking = false;

if(walkRight && !isWinning ) {
vposx += velocity;

walking = true;
TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
Char.setRegion(currentFrame); // seta a textura no frame que tiver
Char.setFlip(false, false);// impede de espelhar (olha só para a direita)
}

if(walkLeft && !isWinning ) {
if(vposx >= 0 ) {
vposx -= velocity;

walking = true;

TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
Char.setRegion(currentFrame);
Char.setFlip(true, false); //espelha olhando para a esquerda
}
}

if (!walking) {
Char.setTexture(tChar);
Char.setFlip(Char.isFlipX(), false); // deixa o frame da a direção que ele ta olhando
}

if(PressedJump && inFloor) { // pulinho apertado
velocityY = jump_strength;
inFloor = false; //sai do chao
jumptimer = 0;

jumpSound.play(0.1f);

}
}
//======================================================================================================================
//FIM DO METODO MOVEchar
//======================================================================================================================

private void DrawGameOver() {
    // limpa a tela com a cor preta
    ScreenUtils.clear(0, 0, 0, 1);
    
    // configura o batch para usar a câmera da UI 
    batch.setProjectionMatrix(uiCamera.combined);
    batch.begin();
    
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    
    // desenha o texto bem no centro da tela
    font.draw(batch, "GAME OVER", screenWidth / 2f - 80, screenHeight / 2f + 20);
    
    if (Controllers.getControllers().size > 0) {
      	font.draw(batch, "PRESS O TO RESET",  screenWidth / 2f - 130, screenHeight / 2f - 140);
      
      }else {
      font.draw(batch, "PRESS ENTER TO RESET", screenWidth / 2f - 170, screenHeight / 2f - 20);
      }
      batch.end();

    // se apertar enter o jogo reseta
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || CONFIRM) {
    	SelectSound.play();
    	game.lifes = 3;
    	game.score = 0;
    	game.coins = 0;
        GameOver = false;
        isDead = false;
        game.setScreen(new RegisterScreen(game));
    }
}

private void DrawDeath() {
	// limpa a tela com a cor preta
    ScreenUtils.clear(0, 0, 0, 1);
    
    // configura o batch para usar a câmera da UI 
    batch.setProjectionMatrix(uiCamera.combined);
    batch.begin();
    
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    
    // desenha o texto bem no centro da tela
    batch.draw(tCharHead,  screenWidth / 2f - 55, screenHeight / 2f + 4, 24, 21);
	font.draw(batch, "x" + game.lifes,  screenWidth / 2f - 20, screenHeight / 2f + 20);// desenha a vida
	font.draw(batch, PlayerName +  "\n" + String.format("%06d", game.score), 20, screenHeight - 20);// desenha o Score (Com zeros do lado "%06d")

	font.draw(batch, "WORLD\n" + namePhase, screenWidth - 300, screenHeight - 20);// desenha o Nome da Fase


	
	


	
	font.draw(batch, "WORLD " + namePhase,  screenWidth / 2f - 85, screenHeight / 2f + 80);// desenha o Nome da Fase


	font.draw(batch, "TIME\n ", screenWidth - 120, screenHeight - 20);// Desenha o tempo



		
		Texture CoinFrameUI = coinAnimation.getKeyFrame(timecoinUI);

		batch.draw(CoinFrameUI, screenWidth - 465, screenHeight - 58, 24, 24);

		font.draw(batch, "x" + String.format("%02d", game.coins), screenWidth - 440, screenHeight - 36); // desenha a moeda
    
    batch.end();
    if(DeadTimer > 0f) {
		DeadTimer -= Gdx.graphics.getDeltaTime();
		
	}else {
		isDead = false;
		DeadTimer = 2.5f;
	    this.show();
	}
    
}
private void DrawPass() {
	// limpa a tela com a cor preta
    ScreenUtils.clear(0, 0, 0, 1);
    
    // configura o batch para usar a câmera da UI 
    batch.setProjectionMatrix(uiCamera.combined);
    batch.begin();
    
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    
    // desenha o texto bem no centro da tela
    batch.draw(tCharHead,  screenWidth / 2f - 40, screenHeight / 2f + 4, 24, 21);
	font.draw(batch, "x" + game.lifes,  screenWidth / 2f, screenHeight / 2f + 20);// desenha a vida
	font.draw(batch, PlayerName +  "\n" + String.format("%06d", game.score), 20, screenHeight - 20);// desenha o Score (Com zeros do lado "%06d")

	font.draw(batch, namePhase + "\nWORLD", screenWidth - 300, screenHeight - 20);// desenha o Nome da Fase


	
	font.draw(batch, namePhase + " WORLD",  screenWidth / 2f - 83, screenHeight / 2f + 80);// desenha o Nome da Fase


	font.draw(batch, "TIME\n ", screenWidth - 120, screenHeight - 20);// Desenha o tempo



		
		Texture CoinFrameUI = coinAnimation.getKeyFrame(timecoinUI);

		batch.draw(CoinFrameUI, screenWidth - 465, screenHeight - 58, 24, 24);

		font.draw(batch, "x" + String.format("%02d", game.coins), screenWidth - 440, screenHeight - 36); // desenha a moeda
    
    batch.end();
    if(DeadTimer > 0f) {
		DeadTimer -= Gdx.graphics.getDeltaTime();
		
	}else {
		ShowPass = false;
		DeadTimer = 2.5f;
		game.setScreen(new FaseFinal(game));
	}
    
}

@Override
public void resize(int width, int height) {
	// TODO Auto-generated method stub
	
}

@Override
public void pause() {
	// TODO Auto-generated method stub
	
}

@Override
public void resume() {
	// TODO Auto-generated method stub
	
}

@Override
public void hide() {
	// TODO Auto-generated method stub
	
}
}