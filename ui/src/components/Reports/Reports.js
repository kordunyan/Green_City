import React, {useEffect, useState} from 'react';
import {Spin, Space, Table} from 'antd';
import { getReport } from '../../api/trash';


const Reports = () => {
  const [reportItems, setReportItems] = useState(null);
  const [isLoading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    getReport().then(({ data }) => {
      setLoading(false);
      setReportItems(data);
    });
  }, []);

  return (
    <div>
      <h3>Агрегований звіт по відходам</h3>
      {reportItems && (
        <div>
          <Table
            columns={[
              {
                title: 'Адрес',
                dataIndex: 'address',
              },
              {
                title: 'Назва',
                dataIndex: 'name',
              },
              {
                title: 'Агрегована вага пластику',
                dataIndex: 'plastic',
                render: value => value || 0,
              },
              {
                title: 'Агрегована вага змішаних відходів',
                dataIndex: 'mixed',
                render: value => value || 0,
              },
              {
                title: 'Агрегована вага органічних відходів',
                dataIndex: 'organic',
                render: value => value || 0,
              },
              {
                title: 'Агрегована вага паперу',
                dataIndex: 'paper',
                render: value => value || 0,
              }
            ]}
            dataSource={reportItems}
          />
        </div>
      )}
      {isLoading && (
        <Space size='middle' style={{ marginTop: 200 }}>
          <Spin size='large' />
        </Space>
      )}
    </div>
  );
};

export default Reports;
