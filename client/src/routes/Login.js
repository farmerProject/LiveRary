import styles from "./Login.module.css";
import { Modal, Button } from "react-bootstrap";
import { useEffect, useState } from "react";

function Login({ showLoginModal, setShowLoginModal }) {
  const [show, setShow] = useState(false);

  const handleClose = () => {
    setShowLoginModal(false);
    setShow(false);
  };

  useEffect(() => {
    setShow(showLoginModal);
  }, []);

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <h1 className="h4 mb-3 fw-normal">
          로그인 및 회원가입해주세요!
        </h1>
      </Modal.Header>
      <Modal.Body>
        <a href="http://localhost:8080/oauth2/authorization/google">
          <img src="/social/google/1x/btn_google_signin_dark_normal_web.png" />
        </a>
      </Modal.Body>
      <Modal.Footer>
        <p className="mt-5 mb-3 text-muted">© 2022–2022</p>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default Login;
