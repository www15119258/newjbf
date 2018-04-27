/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryAggregation (aggregation) {
  return axios.get(config.system.server_path + '/cms/aggregation/query', {params: aggregation})
}

export function addAggregation (aggregation) {
  return axios.post(config.system.server_path + '/cms/aggregation/save', qs.stringify(aggregation))
}

export function updateAggregation (aggregation) {
  return axios.put(config.system.server_path + '/cms/aggregation/update', qs.stringify(aggregation))
}

export function deleteAggregation (id) {
  return axios.delete(config.system.server_path + '/cms/aggregation/delete/' + id)
}
