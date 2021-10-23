import { useState } from 'react';
import { Upload, Modal } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import { withCompanySelector } from '../../hocs/withCompanySelector';

const getBase64 = (file) => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
});

const PhotoGroup = ({ maxPhotosCount, groupName, onChange }) => {
    const [previewVisible, setPreviewVisible] = useState(false);
    const [previewImage, setPreviewImage] = useState('');
    const [previewTitle, setPreviewTitle] = useState('');
    const [fileList, setFileList] = useState([]);

    const onChangeHandler = ({ fileList }) => {
        setFileList(fileList.map(file => {
            const result = { ...file };
            delete result.response;
            return result;
        }));
        console.log('pgoto group', fileList.map(file => file.originFileObj));
        onChange(fileList.map(file => file.originFileObj));
    };

    const onCancel = () => setPreviewVisible(false);

    const onPreview = async file => {
        if (!file.url && !file.preview) {
            file.preview = await getBase64(file.originFileObj);
        }

        setPreviewImage(file.url || file.preview);
        setPreviewVisible(true);
        setPreviewTitle(file.name || file.url.substring(file.url.lastIndexOf('/') + 1));
    };

    const onBeforeUpload = file => {
      console.log('file', file)
      return file;
    };

    return (
        <div>
            <h3>{groupName}</h3>
            <Upload
                listType='picture-card'
                fileList={fileList}
                onPreview={onPreview}
                onChange={onChangeHandler}
                beforeUpload={onBeforeUpload}
            >
                {fileList.length < maxPhotosCount && (
                    <div>
                        <PlusOutlined />
                        <div style={{ marginTop: 8 }}>Upload</div>
                    </div>
                )}
            </Upload>
            <Modal
                visible={previewVisible}
                title={previewTitle}
                footer={null}
                onCancel={onCancel}
            >
                <img alt='example' style={{ width: '100%' }} src={previewImage} />
            </Modal>
        </div>
    );
}

export default PhotoGroup;
