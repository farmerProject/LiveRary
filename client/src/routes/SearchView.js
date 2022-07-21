import { useEffect, useState  } from "react";
import styles from "./Home.module.css";
import Login from "./Login.js";
import axios from "axios";
import Viewer from "./Viewer";
import { useLocation } from 'react-router-dom';
import { Button } from "react-bootstrap";

function SearchView({ showLoginModal, setShowLoginModal }){
    const [definitionList, setDefinitionList] = useState([]);
    const [searching, setSearching] = useState(false);
    let location = useLocation();

    const searchName = location.state.searchName;
    console.log("검색화면::" + searchName);

      const getList = async () => {
        //const url ="http://localhost:8080/api/v1/documents/search";//"https://yts.mx/api/v2/list_movies.json?minimum_rating=9&sort_by=year";
        try{
          const response = await axios.get("http://localhost:8080/api/v1/documents/search" , {params: {keyword : searchName}});
          setDefinitionList(response.data.data.datas);
          const flag = response.data.data.datas.length;
          if(flag == 0){
           setSearching(true);
          }
          else{
           setSearching(false);
          }
        }
        catch(error){
          setSearching(true);
          console.log("searchviewfail");
        }
      };

      useEffect(() => {
        getList();
      }, [searchName]);

    return (

        <div className={styles.home}>
                  {showLoginModal === true ? (
                    <Login showLoginModal={true} setShowLoginModal={setShowLoginModal} />
                  ) : null}
                <div> 
                  {searching ? 
                <div>
                  <div className={styles.resultDiv}>
                    검색된 결과가 없습니다.
                  </div>
                  <div className={styles.addBtn}>
                    <Button variant="outline-dark" className={styles.searchBtn} id="button-search" active href="/writer">
                      용어 추가
                    </Button>
                  </div>
                </div>
                  :
                <div>
                  {definitionList && definitionList.map((definition) => (
                              <Viewer key={definition.id}
                                    id={definition.id}
                                    title={definition.title}
                                    content={definition.content}
                              />

                            ))}
                </div>
                }
                </div>
        </div>
    );
}

export default SearchView;