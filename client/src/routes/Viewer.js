import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/react-editor';
import {Link } from "react-router-dom";
import styles from "./Home.module.css";
// import Prism from 'prismjs';
import 'prismjs/themes/prism.css';

function ViewList({id, content , title}){
    
    return (
    <div>
        <div className={styles.viewer_title}>
                <Link to={`/definitions/{id}`}>{title}</Link>
        </div>
        <div className={styles.viewer_content}>
            <Viewer
            initialValue={content} />
        </div>
        <hr></hr>
    </div>
    );
}

export default ViewList;