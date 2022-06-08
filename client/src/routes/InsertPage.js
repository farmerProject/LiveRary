import { Viewer } from "@toast-ui/react-editor";
import { useEffect, useState } from "react";
import Writer from "./Writer";
import { InputGroup } from "react-bootstrap";
import { FormControl } from "react-bootstrap";
import { Button } from "react-bootstrap";
import styles from "./Home.module.css";
import React, { useRef } from "react";

function InsertPage(){
    
    let [inputValue, setInputValue] = useState("");
    console.log(inputValue);



    return (
        <div>
            <div className={styles.inputDiv}>
                <input type="text" class="form-control" id={styles.title}
                       placeholder="정의하고자 하는 단어나 구절을 적어주세요.." aria-label="Recipient's username" aria-describedby="basic-addon2"
                       onChange={(event) => setInputValue(event.target.value)}
                >
                </input>
            </div>
            <div className={styles.test}>
            <Writer 
           /> 
            </div>
            <div className={styles.formBtn}>
             <Button 
                variant="btn btn-dark" 
                type="submit"
                className="submitBtn">
            Save</Button>{' '}
            <Button 
                variant="btn btn-dark" 
                className="calcelBtn"
                href="/">
            Cancel</Button>
            </div>
       
        </div>
    );
}

export default InsertPage;