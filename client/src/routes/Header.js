import { useEffect, useState } from "react";
import styles from "./Header.module.css";
import { InputGroup } from "react-bootstrap";
import { FormControl } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Dropdown } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
import { Link } from "react-router-dom";
import axios from "axios";

function Header(props){
  const [userName, setUserName] = useState("");
  const [userId, setUserId] = useState("");
  const [userEmail, setUserEmail] = useState("");
  const [userIp, setUserIp] = useState("");

  const imgStyle = {
    height: 35,
    width: 35,
  };
  const request = () => {
      axios
        .get("http://localhost:8080/api/v1/user")
        .then((response) => {
          setUserName(response.data.name);
          props.setUserName(response.data.name);

          setUserId(response.data.id);
          props.setUserId(response.data.id);

          setUserEmail(response.data);
          props.setUserEmail(response.data.email);

        })
        .catch((error) => {});
    };

    const loginBtnClicked = (event) => {
      event.preventDefault();
      props.setShowLoginModal(true);
    };

    const getUserIp = () => {
      axios
      .get("https://geolocation-db.com/json/0f761a30-fe14-11e9-b59f-e53803842572")
        .then((response) => {
          setUserIp(response.data.IPv4);
          props.setUserIp(response.data.IPv4);
          
        })
        .catch((error) => {});
    };
    
    useEffect(() => {
      getUserIp();
      request();
    }, []);

    return (
    <div className={styles.header}>
        <div className={styles.parent}>
            <div className={styles.left_div}>
                <a href="/" className={styles.header_title}>
                  <img src="/LiveRARY.png" />
                </a>
            </div>
            <div className={styles.middle_div}>
              <div className={styles.btn1}>
                <InputGroup className="mb-3" id={styles.input}>
                    <FormControl 
                    placeholder="Search Word here...."
                    aria-label="Search Word here...."
                    aria-describedby="basic-addon2"
                    />
                <Button variant="dark" className={styles.searchBtn} id="button-search" size="lg" active
                href={`/searchView`}
                >
                Search
                </Button>
                </InputGroup>
              </div>
              <div className={styles.btn2}>
                <Button variant="dark" className={styles.searchBtn} id="button-search" size="lg" active href="/writer">
                  +
                </Button>
              </div>
                
                <div className={styles.btn3}>
                {userName === "" ? (
                        <Button variant="dark" className={styles.searchBtn} id="button-search" size="lg" active
                        href="/signin" onClick={loginBtnClicked} >
                        로그인
                        </Button>
                       ) : (
                         <Dropdown>
                           <Dropdown.Toggle variant="white" id="dropdown-basic">
                             <img
                               className={"rounded-circle"}
                               style={imgStyle}
                               src="logo192.png"
                             />
                             &nbsp;
                             <span>Hi there! {userName}</span>
                             &nbsp;
                           </Dropdown.Toggle>

                           <Dropdown.Menu>
                             <Dropdown.Item href="/logout">Logout</Dropdown.Item>
                           </Dropdown.Menu>
                         </Dropdown>
                )}
           
                </div>
            </div>
        </div>
        
    </div>
    

    );
}

export default Header;