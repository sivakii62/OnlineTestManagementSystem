export function parseJwt(token:any){
    
        try {
          return JSON.parse(atob(token.split('.')[1])).sub;
        } catch (e) {
          return null;
        }
    
}