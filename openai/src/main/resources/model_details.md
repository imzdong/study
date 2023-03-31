[doc](https://platform.openai.com/docs/models/overview)

Models
Overview
The OpenAI API is powered by a diverse set of models with different capabilities and price points. You can also make limited customizations to our original base models for your specific use case with fine-tuning.
OpenAI API 由具有不同功能和价位的多种模型提供支持。您还可以通过微调针对您的特定用例对我们的原始基础模型进行有限的定制。

MODELS	DESCRIPTION
GPT-4 Limited beta	A set of models that improve on GPT-3.5 and can understand as well as generate natural language or code
                    一组在 GPT-3.5 上改进的模型，可以理解并生成自然语言或代码
GPT-3.5	A set of models that improve on GPT-3 and can understand as well as generate natural language or code
                    一组在 GPT-3 上改进的模型，可以理解并生成自然语言或代码
DALL·EBeta	A model that can generate and edit images given a natural language prompt
                    给定自然语言提示，可以生成和编辑图像的模型
WhisperBeta	A model that can convert audio into text
                    可以将音频转换为文本的模型
Embeddings	A set of models that can convert text into a numerical form
                    可以将文本转换为数字形式的模型
Moderation	A fine-tuned model that can detect whether text may be sensitive or unsafe
                    可以检测文本是否可能敏感或不安全的微调模型
GPT-3	A set of models that can understand and generate natural language
                    可以理解和生成自然语言的模型
Codex Deprecated	A set of models that can understand and generate code, including translating natural language to code
                    可以理解和生成代码的模型，包括将自然语言翻译为代码
We have also published open source models including Point-E, Whisper, Jukebox, and CLIP.
我们还发布了开源模型，包括 Point-E、Whisper、Jukebox 和 CLIP。

Visit our model index for researchers to learn more about which models have been featured in our research papers and the differences between model series like InstructGPT and GPT-3.5.
访问我们的模型索引，了解有关哪些模型已被我们的研究论文所介绍以及 InstructGPT 和 GPT-3.5 系列模型之间的差异的更多信息。
GPT-4 Limited beta
GPT-4 is a large multimodal model (accepting text inputs and emitting text outputs today, with image inputs coming in the future) that can solve difficult problems with greater accuracy than any of our previous models, thanks to its broader general knowledge and advanced reasoning capabilities. Like gpt-3.5-turbo, GPT-4 is optimized for chat but works well for traditional completions tasks. Learn how to use GPT-4 in our chat guide.
GPT-4 是一个大型多模型模型（今天接受文本输入并发出文本输出，将来将接受图像输入），由于其更广泛的通用知识和先进的推理能力，因此可以比我们以前的任何模型更准确地解决困难问题。与 gpt-3.5-turbo 一样，GPT-4 优化了聊天，但也适用于传统的完成任务。在我们的聊天指南中了解如何使用 GPT-4。
GPT-4 is currently in a limited beta and only accessible to those who have been granted access. Please join the waitlist to get access when capacity is available.
GPT-4 目前处于有限的测试阶段，仅限于已被授予访问权限的人员访问。请加入候补列表，以便在有空余时获得访问权限。
LATEST MODEL	DESCRIPTION	MAX TOKENS	TRAINING DATA
gpt-4	More capable than any GPT-3.5 model, able to do more complex tasks, and optimized for chat. Will be updated with our latest model iteration.	8,192 tokens	Up to Sep 2021
比任何 GPT-3.5 模型都更强大，能够执行更复杂的任务，并针对聊天进行了优化。将使用我们最新的模型迭代进行更新。
gpt-4-0314	Snapshot of gpt-4 from March 14th 2023. Unlike gpt-4, this model will not receive updates, and will only be supported for a three month period ending on June 14th 2023.	8,192 tokens	Up to Sep 2021
2023 年 3 月 14 日的 gpt-4 快照。与 gpt-4 不同，该模型不会收到更新，并且仅在 2023 年 6 月 14 日结束的三个月内提供支持。
gpt-4-32k	Same capabilities as the base gpt-4 mode but with 4x the context length. Will be updated with our latest model iteration.	32,768 tokens	Up to Sep 2021
与基本 gpt-4 模式具有相同的功能，但具有 4 倍的上下文长度。将使用我们最新的模型迭代进行更新。
gpt-4-32k-0314	Snapshot of gpt-4-32 from March 14th 2023. Unlike gpt-4-32k, this model will not receive updates, and will only be supported for a three month period ending on June 14th 2023.	32,768 tokens	Up to Sep 2021
2023 年 3 月 14 日的 gpt-4-32k 快照。与 gpt-4-32k 不同，该模型不会收到更新，并且仅在 2023 年 6 月 14 日结束的三个月内提供支持。
For many basic tasks, the difference between GPT-4 and GPT-3.5 models is not significant. However, in more complex reasoning situations, GPT-4 is much more capable than any of our previous models.
对于许多基本任务来说，GPT-4 和 GPT-3.5 模型之间的差异并不显着。但是，在更复杂的推理情况下，GPT-4 比我们以前的任何模型都更具有能力。

GPT-3.5
GPT-3.5 models can understand and generate natural language or code. Our most capable and cost effective model in the GPT-3.5 family is gpt-3.5-turbo which has been optimized for chat but works well for traditional completions tasks as well.
GPT-3.5 模型可以理解和生成自然语言或代码。我们在 GPT-3.5 家族中最具有能力和成本效益的模型是 gpt-3.5-turbo，该模型已针对聊天进行了优化，但也适用于传统的完成任务。
LATEST MODEL	DESCRIPTION	MAX TOKENS	TRAINING DATA
gpt-3.5-turbo	Most capable GPT-3.5 model and optimized for chat at 1/10th the cost of text-davinci-003. Will be updated with our latest model iteration.	4,096 tokens	Up to Sep 2021
最具有能力的 GPT-3.5 模型，针对聊天进行了优化，成本为 text-davinci-003 的 1/10。将使用我们最新的模型迭代进行更新。
gpt-3.5-turbo-0301	Snapshot of gpt-3.5-turbo from March 1st 2023. Unlike gpt-3.5-turbo, this model will not receive updates, and will only be supported for a three month period ending on June 1st 2023.	4,096 tokens	Up to Sep 2021
2023 年 3 月 1 日的 gpt-3.5-turbo 快照。与 gpt-3.5-turbo 不同，该模型不会收到更新，并且仅在 2023 年 6 月 1 日结束的三个月内提供支持。
text-davinci-003	Can do any language task with better quality, longer output, and consistent instruction-following than the curie, babbage, or ada models. Also supports inserting completions within text.	4,097 tokens	Up to Jun 2021
可以以比居里、巴贝奇或 ada 模型更好的质量、更长的输出和一致的指令遵循来完成任何语言任务。还支持在文本中插入补全。
text-davinci-002	Similar capabilities to text-davinci-003 but trained with supervised fine-tuning instead of reinforcement learning	4,097 tokens	Up to Jun 2021
具有与 text-davinci-003 相似的功能，但是使用监督微调而不是强化学习进行训练
code-davinci-002	Optimized for code-completion tasks	8,001 tokens	Up to Jun 2021
针对代码完成任务进行了优化
We recommend using gpt-3.5-turbo over the other GPT-3.5 models because of its lower cost.
我们建议使用 gpt-3.5-turbo 而不是其他 GPT-3.5 模型，因为它的成本更低。

OpenAI models are non-deterministic, meaning that identical inputs can yield different outputs. Setting temperature to 0 will make the outputs mostly deterministic, but a small amount of variability may remain.
OpenAI 模型是非确定性的，这意味着相同的输入可能会产生不同的输出。将温度设置为 0 将使输出基本上是确定性的，但可能仍然存在一些变化。
Feature-specific models
While the new gpt-3.5-turbo model is optimized for chat, it works very well for traditional completion tasks. The original GPT-3.5 models are optimized for text completion.
虽然新的 gpt-3.5-turbo 模型针对聊天进行了优化，但它非常适合传统的完成任务。原始的 GPT-3.5 模型针对文本完成进行了优化。

Our endpoints for creating embeddings and editing text use their own sets of specialized models.
我们用于创建嵌入和编辑文本的端点使用自己的一组专用模型。

Finding the right model
Experimenting with gpt-3.5-turbo is a great way to find out what the API is capable of doing. After you have an idea of what you want to accomplish, you can stay with gpt-3.5-turbo or another model and try to optimize around its capabilities.
尝试 gpt-3.5-turbo 是了解 API 能够做什么的好方法。在你对你想要完成的事情有了一个想法之后，你可以继续使用 gpt-3.5-turbo 或另一个模型，并尝试优化它的功能。

You can use the GPT comparison tool that lets you run different models side-by-side to compare outputs, settings, and response times and then download the data into an Excel spreadsheet.
你可以使用 GPT 比较工具，它可以让你并排运行不同的模型，以比较输出、设置和响应时间，然后将数据下载到 Excel 电子表格中。

DALL·E Beta
DALL·E is a AI system that can create realistic images and art from a description in natural language. We currently support the ability, given a prommpt, to create a new image with a certain size, edit an existing image, or create variations of a user provided image.
DALL·E 是一个 AI 系统，可以从自然语言中的描述创建逼真的图像和艺术品。我们目前支持给定提示时创建具有特定大小的新图像、编辑现有图像或创建用户提供图像的变体的能力。

The current DALL·E model available through our API is the 2nd iteration of DALL·E with more realistic, accurate, and 4x greater resolution images than the original model. You can try it through the our Labs interface or via the API.
通过我们的 API 提供的当前 DALL·E 模型是 DALL·E 的第 2 次迭代，具有比原始模型更逼真、更准确且分辨率高 4 倍的图像。您可以通过我们的实验室界面或 API 进行试用。

Whisper Beta
Whisper is a general-purpose speech recognition model. It is trained on a large dataset of diverse audio and is also a multi-task model that can perform multilingual speech recognition as well as speech translation and language identification. The Whisper v2-large model is currently available through our API with the whisper-1 model name.
Whisper 是一个通用的语音识别模型。它是在包含多种音频的大型数据集上进行训练的，也是一个多任务模型，可以执行多语种语音识别以及语音翻译和语言识别。目前，Whisper v2-large 模型可以通过我们的 API 使用 whisper-1 模型名称进行访问。

Currently, there is no difference between the open source version of Whisper and the version available through our API. However, through our API, we offer an optimized inference process which makes running Whisper through our API much faster than doing it through other means. For more technical details on Whisper, you can read the paper.
目前，开源版本的 Whisper 和通过我们的 API 可用的版本之间没有区别。但是，通过我们的 API，我们提供了一种优化的推理过程，这使得通过我们的 API 运行 Whisper 比通过其他方式运行 Whisper 快得多。有关 Whisper 的更多技术细节，您可以阅读论文。

Embeddings
Embeddings are a numerical representation of text that can be used to measure the relateness between two pieces of text. Our second generation embedding model, text-embedding-ada-002 is a designed to replace the previous 16 first-generation embedding models at a fraction of the cost. Embeddings are useful for search, clustering, recommendations, anomaly detection, and classification tasks. You can read more about our latest embedding model in the announcement blog post.
嵌入是文本的数值表示，可用于衡量两段文本之间的相关性。我们的第二代嵌入模型 text-embedding-ada-002 旨在以更低的成本替换之前的 16 个第一代嵌入模型。嵌入对于搜索、聚类、推荐、异常检测和分类任务等都很有用。您可以在公告博客文章中阅读有关我们最新嵌入模型的更多信息。

Moderation
The Moderation models are designed to check whether content complies with OpenAI's usage policies. The models provide classification capabilities that look for content in the following categories: hate, hate/threatening, self-harm, sexual, sexual/minors, violence, and violence/graphic. You can find out more in our moderation guide.
Moderation 模型旨在检查内容是否符合 OpenAI 的使用政策。该模型提供了分类功能，用于查找以下类别中的内容：仇恨、仇恨/威胁、自杀、性、性/未成年人、暴力和暴力/图形。您可以在我们的 moderation 指南中找到更多信息。

Moderation models take in an arbitrary sized input that is automatically broken up to fix the models specific context window.
Moderation 模型接受任意大小的输入，该输入会自动分割以适应模型的特定上下文窗口。

MODEL	DESCRIPTION
text-moderation-latest	Most capable moderation model. Accuracy will be slighlty higher than the stable model
最有能力的审核模型。精度会略高于稳定模型
text-moderation-stable	Almost as capable as the latest model, but slightly older.
几乎与最新模型一样有能力，但略有陈旧。
GPT-3
GPT-3 models can understand and generate natural language. These models were superceded by the more powerful GPT-3.5 generation models. However, the original GPT-3 base models (davinci, curie, ada, and babbage) are current the only models that are available to fine-tune.
GPT-3 模型可以理解和生成自然语言。这些模型已被更强大的 GPT-3.5 生成模型所取代。但是，原始的 GPT-3 基本模型（davinci、curie、ada 和 babbage）是目前唯一可以进行微调的模型。

LATEST MODEL	DESCRIPTION	MAX TOKENS	TRAINING DATA
text-curie-001	Very capable, faster and lower cost than Davinci.	2,049 tokens	Up to Oct 2019
非常有能力，比达芬奇更快，成本更低。
text-babbage-001	Capable of straightforward tasks, very fast, and lower cost.	2,049 tokens	Up to Oct 2019
能够完成简单的任务，非常快，成本也低。
text-ada-001	Capable of very simple tasks, usually the fastest model in the GPT-3 series, and lowest cost.	2,049 tokens	Up to Oct 2019
能够完成非常简单的任务，通常是 GPT-3 系列中最快的模型，成本也最低。
davinci	Most capable GPT-3 model. Can do any task the other models can do, often with higher quality.	2,049 tokens	Up to Oct 2019
达芬奇	最有能力的 GPT-3 模型。可以完成其他模型可以完成的任何任务，通常质量更高。
curie	Very capable, but faster and lower cost than Davinci.	2,049 tokens	Up to Oct 2019
库里	非常有能力，但比达芬奇更快，成本更低。
babbage	Capable of straightforward tasks, very fast, and lower cost.	2,049 tokens	Up to Oct 2019
巴贝奇	能够完成简单的任务，非常快，成本也低。
ada	Capable of very simple tasks, usually the fastest model in the GPT-3 series, and lowest cost.	2,049 tokens	Up to Oct 2019
阿达	能够完成非常简单的任务，通常是 GPT-3 系列中最快的模型，成本也最低。
Codex Deprecated
The Codex models are now deprecated. They were descendants of our GPT-3 models that would understand and generate code. Their training data contains both natural language and billions of lines of public code from GitHub. Learn more.
Codex 模型现已弃用。它们是我们的 GPT-3 模型的后代，可以理解和生成代码。它们的训练数据包含自然语言和数十亿行来自 GitHub 的公共代码。了解更多信息。

They’re most capable in Python and proficient in over a dozen languages including JavaScript, Go, Perl, PHP, Ruby, Swift, TypeScript, SQL, and even Shell.
它们在 Python 中最有能力，在十多种语言中都精通，包括 JavaScript、Go、Perl、PHP、Ruby、Swift、TypeScript、SQL 甚至 Shell。

The following Codex models are now deprecated:
以下 Codex 模型现已弃用：

LATEST MODEL	DESCRIPTION	MAX TOKENS	TRAINING DATA
code-davinci-002	Most capable Codex model. Particularly good at translating natural language to code. In addition to completing code, also supports inserting completions within code.	8,001 tokens	Up to Jun 2021
功能最强大的 Codex 型号。特别擅长将自然语言翻译成代码。除了补全代码，还支持在代码中插入补全。
code-davinci-001	Earlier version of code-davinci-002	8,001 tokens	Up to Jun 2021
code-davinci-002的早期版本
code-cushman-002	Almost as capable as Davinci Codex, but slightly faster. This speed advantage may make it preferable for real-time applications.	Up to 2,048 tokens
几乎与达芬奇 Codex 一样有能力，但略快。这种速度优势可能使其更适合实时应用。
code-cushman-001	Earlier version of code-cushman-002	Up to 2,048 tokens
code-cushman-002的早期版本
For more, visit our guide on working with Codex.
更多信息，请访问我们的 Codex 指南。

Model endpoint compatibility
ENDPOINT	MODEL NAME
/v1/chat/completions	gpt-4, gpt-4-0314, gpt-4-32k, gpt-4-32k-0314, gpt-3.5-turbo, gpt-3.5-turbo-0301
/v1/completions	text-davinci-003, text-davinci-002, text-curie-001, text-babbage-001, text-ada-001, davinci, curie, babbage, ada
/v1/edits	text-davinci-edit-001, code-davinci-edit-001
/v1/audio/transcriptions	whisper-1
/v1/audio/translations	whisper-1
/v1/fine-tunes	davinci, curie, babbage, ada
/v1/embeddings	text-embedding-ada-002, text-search-ada-doc-001
/v1/moderations	text-moderation-stable, text-moderation-latest
This list does not include our first-generation embedding models nor our DALL·E models.

Continuous model upgrades
With the release of gpt-3.5-turbo, some of our models are now being continually updated. In order to mitigate the chance of model changes affecting our users in an unexpected way, we also offer model versions that will stay static for 3 month periods. With the new cadence of model updates, we are also giving people the ability to contribute evals to help us improve the model for different use cases. If you are interested, check out the OpenAI Evals repository.
随着 gpt-3.5-turbo 的发布，我们的一些模型现在正在不断更新。为了减少模型更改以意外方式影响我们用户的可能性，我们还提供将在 3 个月内保持静态的模型版本。随着模型更新的新节奏，我们还让人们能够贡献评估，以帮助我们针对不同的用例改进模型。如果您有兴趣，请查看 OpenAI Evals 存储库。
The following models are the temporary snapshots that will be deprecated at the specified date. If you want to use the latest model version, use the standard model names like gpt-4 or gpt-3.5-turbo.
以下模型是将在指定日期弃用的临时快照。如果要使用最新的模型版本，请使用标准模型名称，如 gpt-4 或 gpt-3.5-turbo。

MODEL NAME	DEPRECATION DATE
gpt-3.5-turbo-0301	June 1st, 2023
gpt-4-0314	June 14th, 2023
gpt-4-32k-0314	June 14th, 2023
