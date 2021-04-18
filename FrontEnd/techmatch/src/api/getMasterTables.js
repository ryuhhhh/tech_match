import client from './client'
export default{
    getMasterTables:()=>{
        return new Promise((resolve,reject)=>{
            client
                .get('/api/master/tables')
                .then((result)=>{resolve(result)})
                .catch(err=>{reject(new Error(err.response))})
        })
    }
}
