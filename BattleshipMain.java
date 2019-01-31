import javafx.application.Application;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Scanner;

public class BattleshipMain extends Application 
{
   private GridPane windowScreen = new GridPane();
   private Battleship battle;
   
   @Override
   public void start(Stage primaryStage)
   {  
      this.battle = new Battleship(10, 10);
      startingGame();
      this.windowScreen.setAlignment(Pos.CENTER);
      
      Scene scene = new Scene(this.windowScreen, battle.getPlayerBoard().length * 70, battle.getPlayerBoard()[0].length * 60);
      primaryStage.setTitle("Battleship");
      primaryStage.setScene(scene);
      primaryStage.show();
      
      promptingPlayer();
   }
   
   public void startingGame()
   {
      this.windowScreen.setPadding(new Insets(10, 10, 10, 10));
      
      Text playerBoard = new Text("     Player Board");
      playerBoard.setFont(Font.font("Arial", 20));
      GridPane.setConstraints(playerBoard, 1, 1);
      setBoardValues(this.battle.getPlayerBoard(), 1);
     
      Text computerBoard = new Text("  Computer Board");
      computerBoard.setFont(Font.font("Arial", 20));
      GridPane.setConstraints(computerBoard, 1, 12);
      setBoardValues(this.battle.getComputerBoardDisplay(), 12);      
      
      addSpacing();
      
      this.windowScreen.getChildren().addAll(playerBoard, computerBoard);  
   }
   
   public void setBoardValues(int[][] p, int columnOffset)
   {
      for(int row = 0; row < p.length; row++)
         for(int col = 0; col < p[row].length; col++)
         {
            Rectangle player = new Rectangle(0, 0, 20, 20);
            player.setStroke(Color.BLACK);
            if(p[row][col] == 3)
               player.setFill(Color.BLACK);
            else if(p[row][col] == 2)
               player.setFill(Color.RED);
            else if(p[row][col] == 1)
               player.setFill(Color.WHITE);
            else
               player.setFill(Color.LIGHTGRAY);
            
            this.windowScreen.add(player, row + 4, col + columnOffset);
         }
   }
   
   public void addSpacing()
   {
      for (int i = 0; i < 2; i++)
      {
         Text blankSpace = new Text("       ");
         this.windowScreen.add(blankSpace, i + 2, 1);
         Text blankSpace2 = new Text("       ");
         this.windowScreen.add(blankSpace2, i + 14, 1);
      }
     
      for (int i = 1; i < 11; i++)
      {
         Text number = new Text("" + i);
         number.setFont(Font.font("Arial", 20));
         this.windowScreen.add(number, 3, 11 + i);
         Text numberHorizontal = new Text("" + i);
         numberHorizontal.setFont(Font.font("Arial", 20));
         this.windowScreen.add(numberHorizontal, 3 + i, 11);
      }
   }
   
   public void promptingPlayer()
   {
      TextField xInput = createInput("x", 10);
      TextField yInput = createInput("y", 11);
      Button sendCoordinates = new Button("      Send Coordinates      ");
      this.windowScreen.add(sendCoordinates, 16, 12);
      sendCoordinates.setOnAction( e -> calculatingMove(xInput.getText(), yInput.getText()));       
   }
   
   public TextField createInput(String coord, int colLoc)
   {
      TextField input = new TextField();
      input.setPromptText("Enter " + coord + " coordinate");
      this.windowScreen.add(input, 16, colLoc);
      return input;
   }
   
   public void calculatingMove(String xInput, String yInput)
   {
      int[][] computerActual = this.battle.getComputerBoardHidden();
      int[][] computerDisplay = this.battle.getComputerBoardDisplay();
      int[][] playerBoard = this.battle.getPlayerBoard();
      
      boolean playerWin = false, computerWin = false, checkingHit = false;
      
      int x, y = -1;
      
      Text usedCoord = new Text("             Pick again");
      usedCoord.setFont(Font.font("Arial", 12));
      this.windowScreen.add(usedCoord, 16, 16);
      usedCoord.setOpacity(0.0);
            
      x = Integer.parseInt(xInput) - 1;
      y = Integer.parseInt(yInput) - 1;
      
      if ( x < 0 || x > 9 )
         handleError("x", 14);
        
      if ( y < 0 || y > 9 )
         handleError("y", 15);
      
      if ((x >= 0 && x <= 9) && (y >= 0 && y <= 9))
      {
         checkingHit = computerDisplay[x][y] == 0;
      }
            
      if (checkingHit)
      {
         this.battle.updateComputerBoards(x, y, computerActual, computerDisplay);
         playerWin = this.battle.checkPlayerWin();
         this.battle.computerTurn();
         computerWin = this.battle.checkComputerWin();
         setBoardValues(this.battle.getPlayerBoard(), 1);
         setBoardValues(this.battle.getComputerBoardDisplay(), 12);
      } else {
         usedCoord.setOpacity(1.0);
         FadeTransition fading = creatingFader(usedCoord);
         fading.play();
      }
            
      if (playerWin)
         handleWin("You");
      
      if (computerWin)
         handleWin("Computer");           
   }
   
   public void handleWin(String player)
   {
      Text winner = new Text("       " + player + " won!");
      winner.setFont(Font.font("Arial", 18));
      this.windowScreen.add(winner, 16, 18);
   }
   
   public void handleError(String coord, int colLoc)
   {
      Text error = new Text("       Invalid " + coord + " coordinate.");
   	  error.setFont(Font.font("Arial", 12));
      this.windowScreen.add(error, 16, colLoc);
      error.setOpacity(1.0);
      FadeTransition fading = creatingFader(error);
      fading.play();
   }
   
   private FadeTransition creatingFader(Node node)
   {
      FadeTransition fade = new FadeTransition(Duration.seconds(3), node);
      fade.setFromValue(1);
      fade.setToValue(0);
      return fade;
   }
   
   public static void main(String[] args)
   {
      Application.launch(args);
   }
}