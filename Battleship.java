public class Battleship
{
   private int[][] computerBoardHidden;
   private int[][] computerBoardDisplay;
   private int[][] playerBoard;
   public static int computerCounter;
   public static int playerCounter;
   
   public Battleship(int row, int col) 
   {
      this.computerBoardHidden = new int[row][col];
      this.computerBoardDisplay = new int[row][col];
      this.playerBoard = new int[row][col];
      
      randomizingBoard(this.playerBoard);
      randomizingBoard(this.computerBoardHidden);
   }
   
   public int[][] getPlayerBoard()
   {
      return this.playerBoard;
   }
   
   public int[][] getComputerBoardDisplay()
   {
      return this.computerBoardDisplay;
   }
   
   public int[][] getComputerBoardHidden()
   {
      return this.computerBoardHidden;
   }
   
   public void randomizingBoard(int[][] board)
   {
      int[] ships = {5, 4, 3, 3, 2};
      
      for(int i = 0; i < ships.length; i++)
      {
         int direction = gettingRandomNumber(2); //0 is horizontal, 1 is vertical
         boolean occupied;
         int row = -1, col = -1;
         
         do
         {
            occupied = false;
            if(direction == 0)
            {
               col = gettingRandomNumber(10 - ships[i] + 1);
               row = gettingRandomNumber(10);
               occupied = isOccupiedHorizontal(row, col, ships[i], board);
               
               if(!occupied)
               {
                  for(int x = col; x < col + ships[i]; x++)
                     board[row][x] = 1;
               }
            }
            else
            {
               col = gettingRandomNumber(10);
               row = gettingRandomNumber(10 - ships[i] + 1);
               occupied = isOccupiedVertical(row, col, ships[i], board);
               
               if(!occupied)
               {
                  for(int y = row; y < row + ships[i]; y++)
                     board[y][col] = 1;
               }
            }
         } while(occupied);
      } 
   }
   
   public boolean isOccupiedHorizontal(int row, int col, int shipSize, int[][] board)
   {
      boolean occupied = false;
      
      for(int x = col; x < col + shipSize; x++)
      {
         if(board[row][x] == 1)
            occupied = true;
      }
      
      return occupied;
   }
   
   public boolean isOccupiedVertical(int row, int col, int shipSize, int[][] board)
   {
      boolean occupied = false;
      
      for(int y = row; y < row + shipSize; y++)
      {
         if(board[y][col] == 1)
            occupied = true;
      }
      
      return occupied;
   }
   
   public void updateComputerBoards(int x, int y, int[][] actualBoard, int[][] displayBoard)
   {
      if(actualBoard[x][y] == 1)
      {
         this.computerBoardHidden[x][y] = 2;
         this.computerBoardDisplay[x][y] = 2;
         this.playerCounter++;
      }
      else
      {
         this.computerBoardHidden[x][y] = 3;
         this.computerBoardDisplay[x][y] = 3;
      }
   }
   
   public void computerTurn()
   {
      int row, col;
      boolean occupied = true;
      do
      {
         row = gettingRandomNumber(10);
         col = gettingRandomNumber(10);
         
         if(this.playerBoard[row][col] == 1)
         {
            this.playerBoard[row][col] = 2;
            this.computerCounter++;
            occupied = false;
         }
         else if(this.playerBoard[row][col] == 0)
         {
            this.playerBoard[row][col] = 3;
            occupied = false;
         }
      } while (occupied);
   }
   
   public int gettingRandomNumber(int n)
   {
      int x = (int)(Math.random() * n);
      return x;
   }
   
   public boolean checkPlayerWin()
   {
      return this.playerCounter == 17;
   }
   
   public boolean checkComputerWin()
   {
      return this.computerCounter == 17;
   }
}