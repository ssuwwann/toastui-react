import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/react-editor";
import axios from "axios";
import { useRef } from "react";

const CustomEditor = () => {
  const editorRef = useRef();

  const onChange = () => {
    const data = editorRef.current.getInstance().getHTML();
    console.log(data);
  };

  const onUploadImage = async (blob, cb) => {
    const formData = new FormData();
    formData.append("files", blob);

    const uploadResponse = await axios.post(
      "http://localhost:8080/board/images",
      formData
    );
    const filename = uploadResponse.data;

    /* const getResponse = await axios.get("http://localhost:8080/board/images", {
      params: { filename: encodeURI(filename) },
    });
 */
    const imageUrl = `http://localhost:8080/board/images?filename=${encodeURI(
      filename
    )}`;
    console.log(imageUrl);
    cb(imageUrl);
  };

  return (
    <div>
      <button>저장 버튼</button>
      <Editor
        ref={editorRef}
        onChange={onChange}
        initialValue=" "
        previewStyle="vertical"
        height="600px"
        initialEditType="wysiwyg"
        useCommandShortcut={false}
        language="ko-KR"
        hooks={{
          addImageBlobHook: onUploadImage,
        }}
      />
    </div>
  );
};

export default CustomEditor;
