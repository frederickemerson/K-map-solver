import java.util.*;
class karnaugh_inp
{
    boolean pot(int n)
    {
        /*If we subtract a power of 2 numbers by 1 
         * then all unset bits after the only set bit become set; 
         * and the set bit becomes unset.*/
        return n!=0 && ((n&(n-1)) == 0);
    }

    int getMaxMin(int n,int v)
    {
        int i=n;
        while(true)
        {
            if(pot(i)) return i;
            i+=v;
        }
    }

    block[][] shape(int n)
    {
        int col=getMaxMin(n,1);
        return new block[((int) Math.pow(2,n))/col][col];
    }

    void k_fill(block arr[],int s,int e,int n,int code, int tb)
    {
        if((e-s)%4==0)
        {   
            if(code ==-1)
            {
                k_fill(arr,(s+e)/2,e,n,code,tb);                
                k_fill(arr,s,(s+e)/2,((s+e)/2),-1*code,tb);
                return;
            }
            k_fill(arr,s,(s+e)/2,n,code,tb);
            k_fill(arr,(s+e)/2,e,((s+e)/2),-1*code,tb);
            return;
        }
        if(code==1)
        {
            for(int i=s;i<e;i++)
            {
                arr[i]=new block(n+tb);
                n++;
            }
            return;
        }
        for(int i=e-1;i>=s;i--)
        {
            arr[i]=new block(n+tb);
            n++;
        }
    }

    void fill(block a[][],int code)
    {
        int val=0;
        for(int r=0;r<a.length/2;r++)
        {
            k_fill(a[r],0,a[0].length,0,1,val);
            val+=a[0].length;
        }
        for(int r=a.length-1;r>=a.length/2;r--)
        {
            k_fill(a[r],0,a[0].length,0,1,val);
            val+=a[0].length;
        }
    }

    void print(block a[][],String type)
    {
        System.out.println();
        System.out.println("Karnugh Map:");
        System.out.println();
        for(int r=0;r<a.length;r++)
        {
            for(int c=0;c<a[0].length;c++)
            {
                String p = "10";
                System.out.print(a[r][c].val+"  ");
            }
            System.out.println("\n");
        }
    }

    
    String[] input()
    {
        System.out.println("Enter Cardinal Expression in format(f(X,Y,Z) = S/P(0,1,2....) ) where S for SOP and P for POS : ");
        String in = new Scanner(System.in).nextLine();
        
        String v = in.split("=")[0].trim();
        String x = in.split("=")[1].trim();
        
        v=(v.substring(v.indexOf("(")+1,v.lastIndexOf(")"))).replace(",","");
        
        return new String[] {x.charAt(0)+"",v,x};
    }
    
    void load(int n,block a[][],String x)
    {
        List<Integer> ele = new ArrayList<Integer>();
        int co=1;

        String nums[]=(x.substring(x.indexOf("(")+1,x.lastIndexOf(")"))).split(",");
        
        for(int i=0;i<nums.length;i++)
        {
            ele.add(Integer.parseInt(nums[i]));
        }
        
        for(int r=0;r<a.length;r++)
        {
            for(int c=0;c<a[0].length;c++)
            {
                int t = a[r][c].block_num;
                if(ele.contains(t))
                {
                    a[r][c].val=1;
                    ele.remove(new Integer(t));
                }
            }
        }
    }
    
    void main()
    {   
        System.out.println("WELCOME TO FREDERICK\'S KARNAUGH MAP SOLVER");
        System.out.println();
        
        String r[]=input();
        int n =r[1].length();
        
        block a[][] = shape(n);
        //System.out.println(a.length+" "+a[0].length);
        fill(a,1);
        load(n,a,r[2]);
        print(a,r[0]);
        
        karnaugh_process p = new karnaugh_process();
        p.get(a,r[0],n,r[1]);
        
        System.out.println("Resultant Expression: ");
        System.out.println(p.getResult());
    }
}