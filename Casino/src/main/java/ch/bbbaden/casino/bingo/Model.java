package ch.bbbaden.casino.bingo;

public class Model {

    int Boards;

    public int getBoards() {
        return Boards;
    }

//    private Model(){
//    }
    
    public static Model model = new Model();
    
    public static Model getInstance(){
        return model;
    }
    
    public void setBoards(int Boards) {
        this.Boards = Boards;
    }

}
