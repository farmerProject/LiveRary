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
  //hmp add
  const [userId, setUserId] = useState("");

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

          //hmp add
          setUserId(response.data.id);
          props.setUserId(response.data.id);

        })
        .catch((error) => {});
    };

    const loginBtnClicked = (e) => {
      e.preventDefault();
      props.setShowLoginModal(true);
    };

    useEffect(() => {
      request();
    }, []);

    return (
    <div className={styles.header + " container"}>
        <div className={styles.parent}>
            <div className={styles.left_div}>
                <h1>LiveRARY</h1>
            </div>
            <div className={styles.middle_div}>
                <InputGroup className="mb-3" id={styles.input}>
                    <FormControl 
                    placeholder="Search Word here...."
                    aria-label="Search Word here...."
                    aria-describedby="basic-addon2"
                    />
                <Button variant="outline-secondary" id="button-addon2">
                Search
                </Button>
                </InputGroup>
            </div>
        
            <div className={styles.right_div}>
              {userName === "" ? (
                         <a
                           href="/signin"
                           role="button"
                           className="btn btn-primary"
                           onClick={loginBtnClicked}
                         >
                           로그인
                         </a>
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
    

    );
}

export default Header;