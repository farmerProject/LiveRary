import { useEffect, useState } from "react";
import styles from "./Home.module.css";
import Login from "./Login.js";
import axios from "axios";
import Viewer from "./Viewer";


function Home({ showLoginModal, setShowLoginModal }){
  
    const [definitionList, setDefinitionList] = useState([]);
  
  function getList(){
    const url ="http://localhost:8080/api/v1/definitions/latest/50";//"https://yts.mx/api/v2/list_movies.json?minimum_rating=9&sort_by=year";
    axios.get(url)
    .then(function(response){
    console.log("Test");
    //console.log(response.data);
    //console.log(response.data.data.items.document.title);

    setDefinitionList(response.data.data.items);
      //setDefinitionList(response.data.data.movies); //전체 틀 끝나면 url 수정하기
    })
    .catch(function(error){
      console.log("fail");
    })
  }

  useEffect(() => {
    getList();
  }, []);

    return (
        <div className={styles.home}>
          {showLoginModal === true ? (
            <Login showLoginModal={true} setShowLoginModal={setShowLoginModal} />
          ) : null}
        
        <div>
          {definitionList.map((definition) => (
            <Viewer key={definition.id} 
                  id={definition.id}
                  title={definition.document.title}
                  content={definition.content}
            />

          ))}
        </div> 
        </div>
    );
}

export default Home;