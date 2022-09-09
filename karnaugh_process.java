import java.util.*;
class karnaugh_process
{
    String exp="";
    String  type="";
    String vars="";
    int n=0;
    boolean pot(int n)
    {
        return n!=0 && ((n&(n-1)) == 0);    }
        
    void check(block a[][],int sr,int sc,int er,int ec)
    {

        int nr=a.length;int nc=a[0].length;
        int sum=0;
        for(int r=sr;r<=er;r++) 
        {
            for(int c=sc;c<=ec;c++)
            {
                int pr=(r<nr)?r:r-nr;
                int pc=(c<nc)?c:c-nc;
                //System.out.println(r+" "+nr+" "+pr+" "+c+" "+nc+" "+pc);
                sum+=a[pr][pc].val;
            }
        }

        if(sum!=((er-sr+1)*(ec-sc+1))|| !pot(sum) || sum==0) return;

        List<Integer> bnum = verify(a,sr,sc,er,ec);
        if(bnum==null)
        {
            return;
        }
        
        /*for(int i:bnum)
        {
            System.out.println(i);
        }*/
        
        //print(a,sr,sc,er,ec);
        this.exp+=simplify(bnum)+((this.type.equals("S"))?"+":".");
        //System.out.println("------------------------"); 
    }

    String getResult()
    {
        return this.exp.substring(0,this.exp.length()-1);
    }
    
    List<Integer> verify(block a[][],int sr,int sc,int er,int ec)
    {
        List<Integer> bnum = new ArrayList<Integer>();
        
        int nr=a.length;int nc=a[0].length;boolean ce=true;
        for(int r=sr;r<=er;r++)
        {
            for(int c=sc;c<=ec;c++)
            {
                int pr=(r<nr)?r:r-nr;
                int pc=(c<nc)?c:c-nc;
                if(a[pr][pc].visited==false)
                {
                    ce=false;
                    a[pr][pc].visit();
                }
                bnum.add(a[pr][pc].block_num);
            }
        }
        return (!ce)?bnum:null;
    }
    
    void print(block a[][],int sr,int sc,int er,int ec)
    {
        int nr=a.length;int nc=a[0].length;
        for(int r=sr;r<=er;r++)
        {
            for(int c=sc;c<=ec;c++)
            {
                int pr=(r<nr)?r:r-nr;
                int pc=(c<nc)?c:c-nc;
                System.out.print(a[pr][pc].val+"  ");
            }
            System.out.println();
        }
    }
  
    
    void get(block a[][],String type,int n,String vars)
    {
        this.type=type; this.n=n;this.vars=vars;
        int nr=a.length;int nc=a[0].length;

        for(int sr=nr-1;sr>=0;sr--)
        {
            for(int sc=nc-1;sc>=0;sc--)
            {
                for(int r=nr-1;r>=0;r--)
                {
                    for(int c=nc-1;c>=0;c--)
                    {
                        if(sr==sc && sc==0) continue;
                        check(a,r,c,r+sr,c+sc);
                    }
                }

            }
        }

    }

    String getBinary(int n,int l)
    {
        String res="";
        while(n>=1)
        {
            if(n==1){res="1"+res;break;}
            res=(n%2)+res;
            n=n/2;
        }        
        while(res.length()!=l)
        {
            res="0"+res;
        }        
        return res;
    }
    
    char possible(String[] a,int in)
    {
        char c = a[0].charAt(in);
        for(int i=1;i<a.length;i++)
        {
            if(c!=a[i].charAt(in))
            {
                return ' ';
            }
        }
        return c;
    }
    
    String getRes(String a[])
    {
        String res="";
        /*for(int i=1;i<=this.n;i++)
        {
            code+=(char) (64+i)+"";
        }*/
        
        for(int i=0;i<this.n;i++)
        {
            char c = possible(a,i);
            if(c==' ') {continue;}
            res+=this.vars.charAt(i);
            if((c=='0' && this.type.equals("S"))||(c=='1' && this.type.equals("P")))
            {
                res+="'";
            }
            res+=(this.type.equals("S"))?".":"+";
        }
        if(res.isEmpty())
        {
            return (this.type.equals("S"))?"1":"0";
        }
        return res.substring(0,res.length()-1);
      }
    
    String simplify(List<Integer> bnum)
    {    
        String bins[]=new String[bnum.size()];
        for(int i=0;i<bins.length;i++)
        {
            bins[i]=getBinary(bnum.get(i),this.n);
        }
        return "("+getRes(bins)+")";
    }
}