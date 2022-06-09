import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/react-editor';
import {Link } from "react-router-dom";
import styles from "./Home.module.css";
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';

function ViewList({id, coverImg , title , summary , genres }){
    
    const test = `# markdown`;
    const test2 = `## Hello!`;

    return (
    <div>
        <div className={styles.viewer_title}>
                <Link to={`/definitions/${id}`}>{title}</Link>
        </div>
        <div className={styles.viewer_content}>
            <Viewer
            initialValue={summary} />
        </div>
        <hr></hr>
    </div>
    );
}

export default ViewList;