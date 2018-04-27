/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryComment (comment) {
  return axios.get(config.system.server_path + '/cms/comment/query', {params: comment})
}

export function addComment (comment) {
  return axios.post(config.system.server_path + '/cms/comment/save', qs.stringify(comment))
}

export function replyComment (comment) {
  return axios.post(config.system.server_path + '/cms/comment/reply', qs.stringify(comment))
}

export function updateComment (comment) {
  return axios.put(config.system.server_path + '/cms/comment/update', qs.stringify(comment))
}

export function updateCommentStatus (id, status) {
  return axios.put(config.system.server_path + '/cms/comment/updateStatus', qs.stringify({'id': id, 'status': status}))
}

export function deleteComment (id) {
  return axios.delete(config.system.server_path + '/cms/comment/delete/' + id)
}
