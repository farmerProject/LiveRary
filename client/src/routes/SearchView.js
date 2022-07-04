import { useEffect, useState } from "react";
import styles from "./Home.module.css";
import Login from "./Login.js";
import axios from "axios";
import Viewer from "./Viewer";

function SearchView({ showLoginModal, setShowLoginModal}){
    const [definitionList, setDefinitionList] = useState([]);

      function getList(){
        const url ="http://localhost:8080/api/v1/documents/{title}/definition";//"https://yts.mx/api/v2/list_movies.json?minimum_rating=9&sort_by=year";
        axios.get(url)
        .then(function(response){
          console.log(response.data.items);
          //setDefinitionList(response.data.data.movies); //전체 틀 끝나면 url 수정하기
          setDefinitionList(response.data.items);
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
                          //coverImg={definition.medium_cover_image}
                          //title={definition.title}
                         /* summary={definition.summary}
                          genres={definition.genres}*/
                          />

                  ))}
                </div>
        </div>
    );
}

export default SearchView;