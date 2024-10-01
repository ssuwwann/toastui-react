import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/react-editor";
import { useRef } from "react";

const CustomEditor = () => {
  const editorRef = useRef();

  const onChange = () => {
    const data = editorRef.current.getInstance().getHTML();
    console.log(data);
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
      />
    </div>
  );
};

export default CustomEditor;
