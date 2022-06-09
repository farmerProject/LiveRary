import { useEffect, useState } from "react";
import { InputGroup } from "react-bootstrap";
import { FormControl } from "react-bootstrap";
import { Button } from "react-bootstrap";
import styles from "./Home.module.css";
import React, { useRef } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

//Editor 
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';

import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';

import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';

import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';


function Writer({userId}){

    const [title , setTitle] = useState("");
    const [documentCollection , setDocumentCollection] = useState("");
    let navigate = useNavigate();

	const editorRef = useRef();

    const onChangeTitleText = (event) => {
		console.log(event.target.value);
        const title = event.target.value;

        setTitle(title);
	}
	const onChangeEditorTextHandler = () => {
		console.log("editor:::"+editorRef.current.getInstance().getMarkdown());

        const document = editorRef.current.getInstance().getMarkdown();

        setDocumentCollection(document);
	}

    const onClickSave = async () => {
      const documentDto = {
          title:title,
          writer: userId,
      };///api/v1/definitions [post]/api/v1/documents [post]
      const contentDto = {
        writer: userId,
        content: document,
        document: {
          title: title
        },
      };///api/v1/definitions [post]/api/v1/documents [post]
        await axios 
          .all([axios.post("http://localhost:8080/api/v1/definitions", documentDto)
               ,axios.post("http://localhost:8080/api/v1/documents", contentDto)])
          .then((response) => {
            alert("저장되었습니다.");
          })
          .catch((error) => {
            console.log(error);
          });
        navigate("/");
      };

    return (
        <div>
            <div className={styles.inputDiv}>
                <input type="text" className="form-control" id={styles.title}
                       placeholder="정의하고자 하는 단어나 구절을 적어주세요.." aria-label="Recipient's username" aria-describedby="basic-addon2"
                       onChange={onChangeTitleText}
                >
                </input>
            </div>
            <div className={styles.writer}>
                <Editor
                    previewStyle='vertical'
                    plugins={[colorSyntax, [codeSyntaxHighlight, { highlighter: Prism }]]}
                    height="600px"
                    initialValue="내용을 입력하세요."
                    onChange={onChangeEditorTextHandler}
                    ref={editorRef}
                />
            </div>
            <div className={styles.formBtn}>
                <Button 
                    variant="btn btn-dark" 
                    type="submit"
                    className="submitBtn"
                    onClick={onClickSave}
                    >
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

export default Writer;