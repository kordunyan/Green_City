import {useEffect, useState} from 'react';
import { Descriptions, Spin, Space, Table, Image } from 'antd';
import moment from 'moment';
import CompanySelector from '../../components/CompanySelector';
import {getCompanyHistory, getCompanyInfo} from '../../api/trash';

const getImagePath = url => `http://localhost:8080/images/${url}`;

const CompanyHistory = () => {
  const [companyId, setCompanyId] = useState(null);
  const [companyInfoLoading, setCompanyInfoLoading] = useState(false);
  const [trashExportationsLoading, setTrashExportationsLoading] = useState(false);
  const [companyInfo, setCompanyInfo] = useState(null);
  const [trashExportations, setTrashExportations] = useState(null);

  useEffect(() => {
    if (companyId) {
      setCompanyInfoLoading(true);
      setTrashExportationsLoading(true);
      getCompanyInfo(companyId).then(({ data }) => {
        setCompanyInfo(data);
        setCompanyInfoLoading(false);
      });
      getCompanyHistory(companyId).then(({ data }) => {
        setTrashExportations(data);
        setTrashExportationsLoading(false);
      });
    }
  }, [companyId]);

  const renderImages = images => (
    <>
      {images.map(image => (
        <Space size='middle'>
          <Image width={100} src={getImagePath(image)} />
        </Space>
      ))}
    </>
  );

  return (
    <div>
      {!companyId && <CompanySelector onSelect={setCompanyId} />}
      {companyInfo && (
        <Space size='middle' style={{ padding: 20 }}>
          <Descriptions title='Інформація про ОСББ'>
            <Descriptions.Item label='Назва'>{companyInfo.name}</Descriptions.Item>
            <Descriptions.Item label='Адрес'>{companyInfo.address}</Descriptions.Item>
            <Descriptions.Item label='Експедитор'>{companyInfo.trashCompany}</Descriptions.Item>
          </Descriptions>
        </Space>
      )}
      {!trashExportationsLoading && companyInfoLoading  && (
        <Space size='middle' style={{ marginTop: 200 }}>
          <Spin size='large' />
        </Space>
      )}
      {trashExportations && (
        <Table
          columns={[
            {
              title: 'Вивезення/Миття',
              dataIndex: 'is_wash',
              render: isWash => isWash ? 'Вивезення' : 'Миття'
            },
            {
              title: 'Дата',
              dataIndex: 'date',
              render: date => moment(date).format('M/D/YYYY, HH:mm:ss')
            },
            {
              title: 'Вага',
              dataIndex: 'weight',
              render: weight => weight ? `${weight} кг` : ''
            },
            {
              title: 'Тип мусора',
              dataIndex: 'trash_type',
              render: trashType => {
                switch (trashType) {
                  case 'PLASTIC': {
                    return 'Пластик';
                  }
                  case 'ORGANIC': {
                    return 'Органіка';
                  }
                  case 'PAPER': {
                    return 'Папір';
                  }
                  case 'MIXED': {
                    return 'Змішані';
                  }
                }
              }
            },
            {
              title: 'До',
              dataIndex: 'emptyTrashImages',
              render: renderImages
            },
            {
              title: 'Після',
              dataIndex: 'fullTrashImages',
              render: renderImages
            }
          ]}
          dataSource={trashExportations}
        />
      )}
      {trashExportationsLoading && !companyInfoLoading && (
        <Space size='middle' style={{ marginTop: 200 }}>
          <Spin size='large' />
        </Space>
      )}
      {trashExportationsLoading && companyInfoLoading && (
        <Space size='middle' style={{ marginTop: 200 }}>
          <Spin size='large' />
        </Space>
      )}
    </div>
  );
};

export default CompanyHistory;
