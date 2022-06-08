import Prism from 'prismjs';
// 여기 css를 수정해서 코드 하이라이팅 커스텀 가능
import 'prismjs/themes/prism.css';

import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';

import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';

import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';

import { useEffect, useState  } from "react";
import React, { useRef } from "react";

function Writer(props){

	const editorRef = useRef();

	const onChangeEditorTextHandler = () => {
		console.log(editorRef.current.getInstance().getMarkdown());
	}

	return (

	<Editor
      	previewStyle='vertical'
      	plugins={[colorSyntax, [codeSyntaxHighlight, { highlighter: Prism }]]}
		height="600px"
    	initialValue="내용을 입력하세요."
		onChange={onChangeEditorTextHandler}
		ref={editorRef}
	/>
	);
}

export default Writer;