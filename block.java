class block
{
    int val,block_num;
    boolean visited;
    block(int n)
    {
        this.val=0;this.block_num=n;
        this.visited=false;;
    }
    
    void visit()
    {
        this.visited=true;
    }
    
    void input(int v)
    {
        this.val=v;
    }
    
}